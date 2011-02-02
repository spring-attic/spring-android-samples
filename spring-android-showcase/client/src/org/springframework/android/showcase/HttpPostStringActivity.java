package org.springframework.android.showcase;

import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HttpPostStringActivity extends AbstractAsyncActivity 
{
	protected String TAG = "HttpPostStringActivity";
	
	
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
				final String url = "http://10.0.2.2:8080/spring-android-showcase/sendmessage";

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// Make the network request, posting the message, expecting a String in response from the server
				String response = restTemplate.postForObject(url, _text, String.class);
				
				// Return the response body to display to the user
				return response;
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
