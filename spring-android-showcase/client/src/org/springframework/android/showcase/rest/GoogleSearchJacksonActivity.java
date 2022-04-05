/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.android.showcase.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class GoogleSearchJacksonActivity extends AbstractAsyncListActivity {

	protected static final String TAG = GoogleSearchJacksonActivity.class.getSimpleName();

	private List<GoogleSearchResult> results;

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

		// when this activity starts, initiate an asynchronous HTTP GET request
		new GoogleSearchTask().execute();
	}

	// ***************************************
	// ListActivity methods
	// ***************************************
	@Override
	protected void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
		if (this.results == null) {
			return;
		}

		GoogleSearchResult result = this.results.get(position);
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(result.getUrl())));
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void refreshResults(GoogleSearchResponse response) {
		if (response == null) {
			return;
		}

		this.results = response.getResponseData().getResults();
		setListAdapter(new GoogleSearchResultListAdapter(this, this.results));
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class GoogleSearchTask extends AsyncTask<Void, Void, GoogleSearchResponse> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected GoogleSearchResponse doInBackground(Void... params) {
			try {
				// The URL for making the GET request
				final String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Set a custom MappingJacksonHttpMessageConverter that supports the text/javascript media type
				MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
				messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript")));
				restTemplate.getMessageConverters().add(messageConverter);

				// Perform the HTTP GET request to the Google search API
				GoogleSearchResponse response = restTemplate.getForObject(url, GoogleSearchResponse.class, "GoPivotal");

				return response;
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(GoogleSearchResponse response) {
			dismissProgressDialog();
			refreshResults(response);
		}

	}

}
