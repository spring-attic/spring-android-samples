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
import java.util.List;

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
public class HttpGetParametersActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = HttpGetParametersActivity.class.getSimpleName();
	

	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.http_get_parameters_activity_layout);
		
		// Initiate the request for JSON data when the JSON button is pushed
		final Button buttonJson = (Button) findViewById(R.id.button_json);
		buttonJson.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		new DownloadStateTask().execute(MediaType.APPLICATION_JSON);
            	}
			}
		);
        
		// Initiate the request for XML data when the XML button is pushed
		final Button buttonXml = (Button) findViewById(R.id.button_xml);
		buttonXml.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		new DownloadStateTask().execute(MediaType.APPLICATION_XML);
            	}
			}
		);
	}
	
	
	//***************************************
    // Private methods
    //***************************************	
	private void showState(State state)
	{
		// display a notification to the user with the state
		if (state != null)
		{
			Toast.makeText(this, state.getFormattedName(), Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "No state found with that abbreviation!", Toast.LENGTH_LONG).show();
		}
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadStateTask extends AsyncTask<MediaType, Void, State> 
	{	
		private String _abbreviation;
		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
			
			// retrieve the abbreviation from the EditText field
			EditText editText = (EditText) findViewById(R.id.edit_text_abbreviation);
			
			_abbreviation = editText.getText().toString();
		}
		
		@Override
		protected State doInBackground(MediaType... params) 
		{
			try 
			{
				if (params.length <= 0)
				{
					return null;
				}
				
				MediaType mediaType = params[0];
				
				// The URL for making the GET request
				final String url = getString(R.string.base_uri) + "/state/{abbreviation}";
				
				// Set the Accept header for "application/json" or "application/xml"
				HttpHeaders requestHeaders = new HttpHeaders();
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(mediaType);
				requestHeaders.setAccept(acceptableMediaTypes);
				
				// Populate the headers in an HttpEntity object to use for the request
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
																
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Perform the HTTP GET request
				ResponseEntity<State> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, State.class, _abbreviation);
								
				// Return the state from the ResponseEntity
				return responseEntity.getBody();
			} 
			catch(Exception e) 
			{
				Log.e(TAG, e.getMessage(), e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(State state) 
		{
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			showState(state);
		}
	}
}
