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

public class HttpPostJsonXmlActivity extends AbstractAsyncActivity 
{
	protected String TAG = "HttpPostJsonActivity";
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.http_post_json_xml_activity_layout);
		
		// Initiate the JSON POST request when the JSON button is clicked
		final Button buttonJson = (Button) findViewById(R.id.button_post_json);
		buttonJson.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		new PostMessageTask().execute(MediaType.APPLICATION_JSON);
            	}
			}
		);
		
		// Initiate the XML POST request when the XML button is clicked
		final Button buttonXml = (Button) findViewById(R.id.button_post_xml);
		buttonXml.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		new PostMessageTask().execute(MediaType.APPLICATION_XML);
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
	private class PostMessageTask extends AsyncTask<MediaType, Void, String> 
	{	
		private Message _message;
		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
			
			// build the message object
			EditText editText = (EditText) findViewById(R.id.edit_text_message_id);
			
			_message = new Message();
			
			try
			{
				_message.setId(Integer.parseInt(editText.getText().toString()));
			}
			catch(NumberFormatException e)
			{
				_message.setId(0);
			}
			
			editText = (EditText) findViewById(R.id.edit_text_message_subject);
			_message.setSubject(editText.getText().toString());
			
			editText = (EditText) findViewById(R.id.edit_text_message_text);
			_message.setText(editText.getText().toString());				
		}
		
		@Override
		protected String doInBackground(MediaType... params) 
		{
			try 
			{
				if (params.length <= 0)
				{
					return null;
				}
				
				MediaType mediaType = params[0];
				
				// The URL for making the POST request
				final String url = "http://10.0.2.2:8080/spring-android-showcase/sendmessage";
				
				HttpHeaders requestHeaders = new HttpHeaders();
								
				// Sending a JSON or XML object i.e. "application/json" or "application/xml"
				requestHeaders.setContentType(mediaType);
				
				// Populate the Message object to serialize and headers in an HttpEntity object to use for the request
				HttpEntity<Message> requestEntity = new HttpEntity<Message>(_message, requestHeaders);
				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
								
				// Return the response body to display to the user
				return response.getBody();
			} 
			catch(Exception e) 
			{
				logException(e);
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
