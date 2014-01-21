/*
 * Copyright 2010-2014 the original author or authors.
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

package org.springframework.android.twittersearch;

import java.util.List;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class TwitterSearchResultsActivity extends ListActivity {

	protected static final String TAG = TwitterSearchResultsActivity.class.getSimpleName();

	private ProgressDialog progressDialog;

	private boolean destroyed = false;

	private List<Tweet> tweets;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		String searchValue = getIntent().getStringExtra(
				"org.springframework.android.showcase.TwitterSearchResultsActivity.search");
		Log.d(TAG, searchValue);
		new TwitterSearchTask().execute(searchValue);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroyed = true;
	}

	// ***************************************
	// ListActivity methods
	// ***************************************
	@Override
	protected void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
		if (this.tweets == null) {
			return;
		}

		Tweet tweet = tweets.get(position);
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tweet.getUrl())));
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showLoadingProgressDialog() {
		this.showProgressDialog("Searching Twitter...");
	}

	private void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	private void dismissProgressDialog() {
		if (this.progressDialog != null && !destroyed) {
			this.progressDialog.dismiss();
		}
	}

	private void refreshResults(ResponseEntity<TwitterSearchResults> response) {
		if (response == null) {
			return;
		}

		TwitterSearchResults results = response.getBody();
		setListAdapter(new TweetListAdapter(this, results.getResults()));
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class TwitterSearchTask extends AsyncTask<String, Void, ResponseEntity<TwitterSearchResults>> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected ResponseEntity<TwitterSearchResults> doInBackground(String... params) {
			try {
				// The URL for making the GET request
				final String url = "http://search.twitter.com/search.json?q={query}&rpp=20";

				// Add the gzip Accept-Encoding header to the request. This is not needed for Gingerbread and newer versions of Android
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

				// Retrieve the parameter passed into the async task
				String searchValue = params[0];

				// Perform the HTTP GET request
				ResponseEntity<TwitterSearchResults> response = restTemplate.exchange(url, HttpMethod.GET,
						new HttpEntity<Object>(requestHeaders), TwitterSearchResults.class, searchValue);

				return response;
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(ResponseEntity<TwitterSearchResults> result) {
			dismissProgressDialog();
			refreshResults(result);
		}

	}

}
