package org.springframework.android.showcase;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HttpGetParametersActivity extends AbstractAsyncActivity 
{

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
		buttonJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	new DownloadStateTask().execute(MediaType.APPLICATION_JSON);
            }
        });
        
		// Initiate the request for XML data when the XML button is pushed
		final Button buttonXml = (Button) findViewById(R.id.button_xml);
		buttonXml.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	new DownloadStateTask().execute(MediaType.APPLICATION_XML);
            }
        });
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
				if (params.length == 0)
				{
					return null;
				}
				
				MediaType mediaType = params[0];
				
				// The URL for making the GET request
				final String url = "http://10.0.2.2:8080/spring-android-showcase/state/{abbreviation}";
				
				// Set the header for requesting JSON data
				// The media type is passed in via the method parameter from the button click event
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(mediaType);
				
				// Populate the headers in an HttpEntity object to use for the request
				HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
								
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Perform the HTTP GET request
				ResponseEntity<State> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, State.class, _abbreviation);
								
				// Return the state from the ResponseEntity
				return responseEntity.getBody();
			} 
			catch(Exception e) 
			{
				logException(e);
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
