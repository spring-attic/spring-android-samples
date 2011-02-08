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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 */
public class HttpGetJsonActivity extends AbstractAsyncListActivity 
{
	protected String TAG = "HttpGetActivity";
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		// when this activity starts, initiate an asynchronous HTTP GET request
		new DownloadStatesTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //*************************************** 
	private void refreshStates(List<State> states) 
	{	
		if (states == null) 
		{
			return;
		}
		
		StatesListAdapter adapter = new StatesListAdapter(this, states);
		setListAdapter(adapter);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadStatesTask extends AsyncTask<Void, Void, List<State>> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}
		
		@Override
		protected List<State> doInBackground(Void... params) 
		{
			try 
			{
				// The URL for making the GET request
				final String url = getString(R.string.base_uri) + "/states";
				
				// Set the Accept header for "application/json"
				HttpHeaders requestHeaders = new HttpHeaders();
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
				requestHeaders.setAccept(acceptableMediaTypes);
				
				// Populate the headers in an HttpEntity object to use for the request
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Perform the HTTP GET request
				ResponseEntity<State[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, State[].class);
								
				// convert the array to a list and return it
				return Arrays.asList(responseEntity.getBody());
			} 
			catch(Exception e) 
			{
				logException(e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<State> result) 
		{
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			refreshStates(result);
		}
	}
}