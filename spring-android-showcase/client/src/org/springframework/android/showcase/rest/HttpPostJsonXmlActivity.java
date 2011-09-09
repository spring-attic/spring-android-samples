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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */
public class HttpPostJsonXmlActivity extends AbstractAsyncActivity {

    protected static final String TAG = HttpPostJsonXmlActivity.class.getSimpleName();

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_post_json_xml_activity_layout);

        // Initiate the JSON POST request when the JSON button is clicked
        final Button buttonJson = (Button) findViewById(R.id.button_post_json);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new PostMessageTask().execute(MediaType.APPLICATION_JSON);
            }
        });

        // Initiate the XML POST request when the XML button is clicked
        final Button buttonXml = (Button) findViewById(R.id.button_post_xml);
        buttonXml.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new PostMessageTask().execute(MediaType.APPLICATION_XML);
            }
        });
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult(String result) {
        if (result != null) {
            // display a notification to the user with the response message
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
        }
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class PostMessageTask extends AsyncTask<MediaType, Void, String> {

        private Message message;

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showLoadingProgressDialog();

            // build the message object
            EditText editText = (EditText) findViewById(R.id.edit_text_message_id);

            message = new Message();

            try {
                message.setId(Integer.parseInt(editText.getText().toString()));
            } catch (NumberFormatException e) {
                message.setId(0);
            }

            editText = (EditText) findViewById(R.id.edit_text_message_subject);
            message.setSubject(editText.getText().toString());

            editText = (EditText) findViewById(R.id.edit_text_message_text);
            message.setText(editText.getText().toString());
        }

        @Override
        protected String doInBackground(MediaType... params) {
            try {
                if (params.length <= 0) {
                    return null;
                }

                MediaType mediaType = params[0];

                // The URL for making the POST request
                final String url = getString(R.string.base_uri) + "/sendmessage";

                HttpHeaders requestHeaders = new HttpHeaders();

                // Sending a JSON or XML object i.e. "application/json" or
                // "application/xml"
                requestHeaders.setContentType(mediaType);

                // Populate the Message object to serialize and headers in an
                // HttpEntity object to use for the request
                HttpEntity<Message> requestEntity = new HttpEntity<Message>(message, requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Make the network request, posting the message, expecting a
                // String in response from the server
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
            // after the network request completes, hid the progress indicator
            dismissProgressDialog();

            // return the response body to the calling class
            showResult(result);
        }

    }

}
