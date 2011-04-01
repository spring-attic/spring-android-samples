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
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class HttpPostMultiValueMapActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = HttpPostMultiValueMapActivity.class.getSimpleName();
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.http_post_multi_value_activity_layout);
		
		// Initiate the JSON POST request when the JSON button is clicked
		final Button buttonJson = (Button) findViewById(R.id.button_submit);
		buttonJson.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		new PostMessageTask().execute();
            	}
			}
		);
	}
		
	
	//***************************************
    // Private methods
    //***************************************	
	private void showResult(String result)
	{
		if (result != null)
		{
			// display a notification to the user with the response message
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
		}
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class PostMessageTask extends AsyncTask<Void, Void, String> 
	{	
		private MultiValueMap<String, String> _message;
		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
			
			// assemble the map
			_message = new LinkedMultiValueMap<String, String>();
			
			EditText editText = (EditText) findViewById(R.id.edit_text_message_id);
			_message.add("id", editText.getText().toString());
			
			editText = (EditText) findViewById(R.id.edit_text_message_subject);
			_message.add("subject", editText.getText().toString());
			
			editText = (EditText) findViewById(R.id.edit_text_message_text);
			_message.add("text", editText.getText().toString());
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{
			try 
			{				
				// The URL for making the POST request
				final String url = getString(R.string.base_uri) + "/sendmessagemap";
				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<String> response = restTemplate.postForEntity(url, _message, String.class);
								
				// Return the response body to display to the user
				return response.getBody();
			} 
			catch(Exception e) 
			{
				Log.e(TAG, e.getMessage(), e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			// after the network request completes, hid the progress indicator
			dismissProgressDialog();
			
			// return the response body to the calling class
			showResult(result);
		}
	}
}
