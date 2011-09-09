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
package org.springframework.android.showcase.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
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
        if (results == null) {
            return;
        }

        GoogleSearchResult result = results.get(position);
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
        setListAdapter(new GoogleSearchResultListAdapter(this, results));
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class GoogleSearchTask extends AsyncTask<Void, Void, GoogleSearchResponse> {

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
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
                MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
                messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "javascript")));
                List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                messageConverters.add(messageConverter);
                restTemplate.setMessageConverters(messageConverters);

                // Perform the HTTP GET request to the Google search API
                GoogleSearchResponse response = restTemplate.getForObject(url, GoogleSearchResponse.class, "VMware");

                return response;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(GoogleSearchResponse response) {
            // hide the progress indicator when the network request is complete
            dismissProgressDialog();

            // return the Google results
            refreshResults(response);
        }

    }

}
