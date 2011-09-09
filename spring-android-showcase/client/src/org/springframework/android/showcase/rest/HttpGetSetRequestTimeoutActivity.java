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

import java.net.SocketTimeoutException;

import org.springframework.android.showcase.AbstractAsyncActivity;
import org.springframework.android.showcase.R;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Roy Clarkson
 */
public class HttpGetSetRequestTimeoutActivity extends AbstractAsyncActivity {

    protected static final String TAG = HttpGetSetRequestTimeoutActivity.class.getSimpleName();

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_get_set_request_timeout_activity_layout);

        final Button buttonJson = (Button) findViewById(R.id.button_submit);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute();
            }
        });
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResponse(String response) {
        if (response != null) {
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
        }
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class RequestTask extends AsyncTask<Void, Void, String> {

        private String serverDelay;

        private int requestTimeout;

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showLoadingProgressDialog();

            EditText editText = (EditText) findViewById(R.id.edit_text_server_delay);
            serverDelay = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_text_request_timeout);
            try {
                requestTimeout = Integer.parseInt(editText.getText().toString()) * 1000;
            } catch (NumberFormatException e) {
                requestTimeout = 10000; // default to 10 seconds
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // The URL for making the GET request
                final String url = getString(R.string.base_uri) + "/delay/{seconds}";

                // Initialize a request factory, setting the request timeout
                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                requestFactory.setReadTimeout(requestTimeout);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate(requestFactory);

                // Perform the HTTP GET request
                String response = restTemplate.getForObject(url, String.class, serverDelay);

                // Return the state from the ResponseEntity
                return response;
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getMessage(), e);
                if (e.getCause() instanceof SocketTimeoutException) {
                    return "Request timed out";
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return "An error occurred";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            // hide the progress indicator when the network request is complete
            dismissProgressDialog();

            // return the list of states
            showResponse(response);
        }

    }

}
