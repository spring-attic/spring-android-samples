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
package org.springframework.android.showcase;

import java.util.List;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ServiceProviderConnection;
import org.springframework.social.connect.sqlite.SqliteConnectionRepository;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.TwitterApi;
import org.springframework.social.twitter.connect.TwitterServiceProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * @author Roy Clarkson
 */
public class TwitterConnectController 
{
	private static final String TWITTER_CONSUMER_TOKEN = "YR571S2JiVBOFyJS5MEg";
	private static final String TWITTER_CONSUMER_TOKEN_SECRET = "Kb8hS0luftwCJX3qVoyiLUMfZDtK1EozFoUkjNLUMx4";
	private static final String OAUTH_CALLBACK_URL = "x-org-springsource-android-showcase://oauth-response";
	private static final String DEFAULT_CONNECT_ACCOUNT_ID = "default";
	private static final String TWITTER_PREFERENCES = "TwitterConnectPreferences";
	private static final String REQUEST_TOKEN_KEY = "request_token";
	private static final String REQUEST_TOKEN_SECRET_KEY = "request_token_secret";
	
	private final Context _context;
	private static TwitterServiceProvider _sharedTwitterServiceProvider;

	
	//***************************************
    // Constructors
    //***************************************
	public TwitterConnectController(Context context)
	{
		_context = context;
	}
	
	
	//***************************************
    // Accessor methods
    //***************************************	
	public String getOauthCallbackUrl()
	{
		return OAUTH_CALLBACK_URL;
	}
	
	public TwitterServiceProvider getTwitterServiceProvider() 
	{
		if (_sharedTwitterServiceProvider == null) 
		{
			_sharedTwitterServiceProvider = new TwitterServiceProvider(
					TWITTER_CONSUMER_TOKEN, 
					TWITTER_CONSUMER_TOKEN_SECRET,
					new SqliteConnectionRepository(_context, Encryptors.noOpText()));
		}
		
		return _sharedTwitterServiceProvider;
	}
	
	public TwitterApi getTwitterApi() 
	{
		if (getTwitterServiceProvider().isConnected(DEFAULT_CONNECT_ACCOUNT_ID))
		{
			return getTwitterServiceProvider().getConnections(DEFAULT_CONNECT_ACCOUNT_ID).get(0).getServiceApi();
		}

		return null;
	}
	
	public boolean isConnected() 
	{
		return getTwitterServiceProvider().isConnected(DEFAULT_CONNECT_ACCOUNT_ID);
	}
	
	public boolean isCallbackUrl(Uri uri) 
	{
		return (uri != null && Uri.parse(OAUTH_CALLBACK_URL).getScheme().equals(uri.getScheme())); 
	}
	
	public String getTwitterAuthorizeUrl() 
	{
		// Fetch a one time use Request Token from Twitter
		OAuthToken requestToken = getTwitterServiceProvider().getOAuthOperations().fetchNewRequestToken(OAUTH_CALLBACK_URL);
		
		// save the Request Token to be used later
		SharedPreferences preferences = _context.getSharedPreferences(TWITTER_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
		editor.commit();	
		
		// Generate the Twitter authorization url to be used in the browser or web view
		return getTwitterServiceProvider().getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), OAUTH_CALLBACK_URL);
	}
		
	public void updateTwitterAccessToken(String verifier) 
	{
		// Retrieve the Request Token that we saved earlier
		SharedPreferences preferences = _context.getSharedPreferences(TWITTER_PREFERENCES, Context.MODE_PRIVATE);		
		String token = preferences.getString(REQUEST_TOKEN_KEY, null);
		String secret = preferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
		OAuthToken requestToken = new OAuthToken(token, secret);
		
		if (token == null || secret == null) 
		{
			return;
		}

		// Authorize the Request Token
		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
		
		// Exchange the Authorized Request Token for the Access Token
		OAuthToken accessToken = getTwitterServiceProvider().getOAuthOperations().exchangeForAccessToken(authorizedRequestToken);
		
		// The Request Token is no longer needed, so it can be removed
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(REQUEST_TOKEN_KEY);
		editor.remove(REQUEST_TOKEN_SECRET_KEY);
		editor.commit();
		
		// Persist the connection and Access Token. Once done, the connection
		// is stored in the local SQLite database for use next time the user
		// opens the app.
		getTwitterServiceProvider().connect(DEFAULT_CONNECT_ACCOUNT_ID, accessToken);
	}
	
	public void disconnect()
	{
		if (getTwitterServiceProvider().isConnected(DEFAULT_CONNECT_ACCOUNT_ID))
		{
			// get all the twitter connections for our default Spring Social account id
			List<ServiceProviderConnection<TwitterApi>> twitterConnections = getTwitterServiceProvider().getConnections(DEFAULT_CONNECT_ACCOUNT_ID);
			
			// we are only working with a single Twitter connection, so get the first one from the list
			ServiceProviderConnection<TwitterApi> defaultTwitterConnection = twitterConnections.get(0);
			
			// disconnect will remove the record from the local database managed by Spring Social
			defaultTwitterConnection.disconnect();
		}
	}
	
	
	//***************************************
    // Private methods
    //***************************************
//	private OAuth1Operations getTwitterOAuthOperations() 
//	{
//		return getTwitterServiceProvider().getOAuthOperations();
//	}
//	
//	private SharedPreferences getTwitterPreferences()
//	{
//		return _context.getSharedPreferences(TWITTER_PREFERENCES, Context.MODE_PRIVATE);
//	}
//	
//	private void saveRequestToken(OAuthToken requestToken) 
//	{
//		SharedPreferences.Editor editor = getTwitterPreferences().edit();
//		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
//		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
//		editor.commit();		
//	}
//	
//	private OAuthToken getSavedRequestToken() 
//	{
//		SharedPreferences preferences = getTwitterPreferences();		
//		String token = preferences.getString(REQUEST_TOKEN_KEY, null);
//		String secret = preferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
//		return new OAuthToken(token, secret);
//	}
//
//	private void deleteSavedRequestToken() 
//	{
//		SharedPreferences.Editor editor = getTwitterPreferences().edit();
//		editor.remove(REQUEST_TOKEN_KEY);
//		editor.remove(REQUEST_TOKEN_SECRET_KEY);
//		editor.commit();
//	}
}
