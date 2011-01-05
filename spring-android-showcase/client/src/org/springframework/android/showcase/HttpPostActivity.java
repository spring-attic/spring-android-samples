package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HttpPostActivity extends Activity 
{
	private static final String TAG = "HttpPostActivity";
	private ProgressDialog _progressDialog;
	
	
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
		
		// when this activity starts, initiate an asynchronous POST request,
		new PostMessageTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showLoadingProgressDialog() 
	{
		_progressDialog = ProgressDialog.show(this, "",  "Loading. Please wait...", true);
	}
		
	private void dismissProgressDialog() 
	{
		if (_progressDialog != null) {
			_progressDialog.dismiss();
		}
	}
	
	private void logException(Exception e) 
	{
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}
	
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
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// The HttpComponentsClientHttpRequestFactory uses the org.apache.http package to make network requests
				restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				
				// Add the message parameter and its value 
				Map<String, String> postParams = Collections.singletonMap("message", "this is a test post message");
				
				// The URL for making the POST request
				final String url = "http://10.0.2.2:8080/spring-android-showcase/sendmessage/";
				
				// Make the network request, posting the message, expecting a
				// String in response from the server
				ResponseEntity<String> response = restTemplate.postForEntity(url, postParams, String.class);
				
				// Log and return the response body
				Log.i(TAG, response.getBody());
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
