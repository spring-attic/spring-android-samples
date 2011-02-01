package org.springframework.android.showcase;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;

public class HttpGetXmlActivity extends AbstractAsyncListActivity 
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
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

                String base_uri = getString(R.string.base_uri);

				// Initiate the HTTP GET request
				StateList stateList = restTemplate.getForObject(base_uri + "/states-xml", StateList.class);
				
				return stateList.getStates();
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
