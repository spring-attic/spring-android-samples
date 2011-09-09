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

import org.springframework.android.showcase.AbstractAsyncActivity;
import org.springframework.android.showcase.R;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Roy Clarkson
 */
public class HttpPostFormDataActivity extends AbstractAsyncActivity {

    protected static final String TAG = HttpPostFormDataActivity.class.getSimpleName();

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.http_post_form_data_layout);

        // Initiate the POST request when the button is selected
        final Button button = (Button) findViewById(R.id.button_post_form_data);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new PostMessageTask().execute();
            }
        });
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult(String result) {
        if (result != null) {
            // display a notification to the user with the response information
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
        }
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class PostMessageTask extends AsyncTask<Void, Void, String> {

        private MultiValueMap<String, Object> formData;

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showLoadingProgressDialog();

            Resource resource = new ClassPathResource("res/drawable/spring09_logo.png");

            // populate the data to post
            formData = new LinkedMultiValueMap<String, Object>();
            formData.add("description", "Spring logo");
            formData.add("file", resource);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // The URL for making the POST request
                final String url = getString(R.string.base_uri) + "/postformdata";

                HttpHeaders requestHeaders = new HttpHeaders();

                // Sending multipart/form-data
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the
                // request
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Make the network request, posting the message, expecting a String in response from the server
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                // Return the response body to display to the user
                return response.getBody();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();

            // return the response body to the calling class
            showResult(result);
        }

    }

}
