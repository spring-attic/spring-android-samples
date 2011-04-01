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
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class GoogleSearchActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = GoogleSearchActivity.class.getSimpleName();
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.google_search_activity_layout);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		// when this activity starts, initiate an asynchronous HTTP GET request
		new GoogleSearchTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //*************************************** 
	private void refreshResults(String result) 
	{	
		if (result == null) 
		{
			return;
		}
		
		TextView textViewResults = (TextView) findViewById(R.id.text_view_results);
		textViewResults.setText(result);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class GoogleSearchTask extends AsyncTask<Void, Void, String> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{
			try 
			{
				// The URL for making the GET request
				final String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";
				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Perform the HTTP GET request to the Google search API
				String result = restTemplate.getForObject(url, String.class, "SpringSource");
				
				return result;
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
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			refreshResults(result);
		}
	}
}
