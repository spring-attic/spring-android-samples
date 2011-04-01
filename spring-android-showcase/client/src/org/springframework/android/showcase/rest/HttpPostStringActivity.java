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
public class HttpPostStringActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = HttpPostStringActivity.class.getSimpleName();
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.http_post_string_activity_layout);
		
		// Initiate the POST request when the button is clicked
		final Button button = (Button) findViewById(R.id.button_post_string);
		button.setOnClickListener(new View.OnClickListener() 
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
		// display a notification to the user with the response message
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class PostMessageTask extends AsyncTask<Void, Void, String> 
	{	
		private String _text;
		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
			
			// retrieve the message text from the EditText field
			EditText editText = (EditText) findViewById(R.id.edit_text_message);
			
			_text = editText.getText().toString();
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{
			try 
			{
				// The URL for making the POST request
				final String url = getString(R.string.base_uri) + "/sendmessage";

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Make the network request, posting the message, expecting a String in response from the server
				String response = restTemplate.postForObject(url, _text, String.class);
				
				// Return the response body to display to the user
				return response;
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
