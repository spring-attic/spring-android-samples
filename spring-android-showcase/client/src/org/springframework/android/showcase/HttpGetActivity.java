package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;

public class HttpGetActivity extends ListActivity 
{
	private static final String TAG = "HttpGetActivity";
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
		
		// when this activity starts, initiate an asynchronous HTTP GET request
		new DownloadStatesTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	
	/**
	 * Displays the list of states in the UI 
	 */
	private void refreshStates(List<State> statesList) 
	{	
		if (statesList == null) 
		{
			return;
		}
		
		List<Map<String,String>> stateMaps = new ArrayList<Map<String,String>>();
		
		for (State state : statesList) 
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", state.getName());
			map.put("abbreviation", state.getAbbreviation());
			stateMaps.add(map);
		}		
		
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				stateMaps,
				R.layout.states_list_item,
				new String[] { "name", "abbreviation" },
				new int[] { R.id.name, R.id.abbreviation } );
		
		this.setListAdapter(adapter);
	}
	
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
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				
				// The HttpComponentsClientHttpRequestFactory uses the org.apache.http package to make network requests
				restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				
				// The URL for making the GET request
				final String url = "http://10.0.2.2:8080/spring-android-showcase/states";
				
				// Initiate the HTTP GET request, expecting an array of State objects in response
				State[] states = restTemplate.getForObject(url, State[].class);
				
				// convert the array to a list and return it
				return Arrays.asList(states);
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
