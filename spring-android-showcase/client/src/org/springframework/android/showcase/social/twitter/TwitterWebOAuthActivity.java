/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.android.showcase.social.twitter;

import org.springframework.android.showcase.AbstractWebViewActivity;
import org.springframework.android.showcase.R;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * @author Roy Clarkson
 */
public class TwitterWebOAuthActivity extends AbstractWebViewActivity {

    @SuppressWarnings("unused")
    private static final String TAG = TwitterWebOAuthActivity.class.getSimpleName();

    private static final String REQUEST_TOKEN_KEY = "request_token";

    private static final String REQUEST_TOKEN_SECRET_KEY = "request_token_secret";

    private ConnectionRepository connectionRepository;

    private TwitterConnectionFactory connectionFactory;

    private SharedPreferences twitterPreferences;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionRepository = getApplicationContext().getConnectionRepository();
        this.connectionFactory = getApplicationContext().getTwitterConnectionFactory();
        this.twitterPreferences = getSharedPreferences("TwitterConnectPreferences", Context.MODE_PRIVATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Uri uri = getIntent().getData();
        if (uri != null) {
            String oauthVerifier = uri.getQueryParameter("oauth_verifier");

            if (oauthVerifier != null) {
                getWebView().clearView();
                new TwitterPostConnectTask().execute(oauthVerifier);
            }
        } else {
            new TwitterPreConnectTask().execute();
        }
    }

    // ***************************************
    // Private methods
    // ***************************************
    private String getOAuthCallbackUrl() {
        return getString(R.string.twitter_oauth_callback_url);
    }

    private void displayTwitterAuthorization(OAuthToken requestToken) {
        // save for later use
        saveRequestToken(requestToken);

        // Generate the Twitter authorization URL to be used in the browser or
        // web view
        String authUrl = connectionFactory.getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        // display the twitter authorization screen
        getWebView().loadUrl(authUrl);
    }

    private void displayTwitterOptions() {
        Intent intent = new Intent();
        intent.setClass(this, TwitterActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveRequestToken(OAuthToken requestToken) {
        SharedPreferences.Editor editor = twitterPreferences.edit();
        editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
        editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
        editor.commit();
    }

    private OAuthToken retrieveRequestToken() {
        String token = twitterPreferences.getString(REQUEST_TOKEN_KEY, null);
        String secret = twitterPreferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
        return new OAuthToken(token, secret);
    }

    private void deleteRequestToken() {
        twitterPreferences.edit().clear().commit();
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class TwitterPreConnectTask extends AsyncTask<Void, Void, OAuthToken> {

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showProgressDialog("Initializing OAuth Connection...");
        }

        @Override
        protected OAuthToken doInBackground(Void... params) {
            // Fetch a one time use Request Token from Twitter
            return connectionFactory.getOAuthOperations().fetchRequestToken(getOAuthCallbackUrl(), null);
        }

        @Override
        protected void onPostExecute(OAuthToken requestToken) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            displayTwitterAuthorization(requestToken);
        }

    }

    private class TwitterPostConnectTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showProgressDialog("Finalizing OAuth Connection...");
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params.length <= 0) {
                return null;
            }

            final String verifier = params[0];

            OAuthToken requestToken = retrieveRequestToken();

            // Authorize the Request Token
            AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);

            // Exchange the Authorized Request Token for the Access Token
            OAuthToken accessToken = connectionFactory.getOAuthOperations().exchangeForAccessToken(authorizedRequestToken, null);

            deleteRequestToken();

            // Persist the connection and Access Token to the repository
            Connection<Twitter> connection = connectionFactory.createConnection(accessToken);

            try {
                connectionRepository.addConnection(connection);
            } catch (DuplicateConnectionException e) {
                // connection already exists in repository!
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            displayTwitterOptions();
        }

    }

}
