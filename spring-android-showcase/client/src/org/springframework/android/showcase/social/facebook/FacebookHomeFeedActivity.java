package org.springframework.android.showcase.social.facebook;

import java.util.List;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.social.facebook.types.FeedEntry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class FacebookHomeFeedActivity extends AbstractAsyncListActivity 
{
	protected static final String TAG = FacebookHomeFeedActivity.class.getSimpleName();

	private FacebookConnectController _facebookConnectController;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_facebookConnectController = new FacebookConnectController(getApplicationContext());
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		new FetchWallFeedTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showResult(List<FeedEntry> entries)
	{
		FacebookFeedListAdapter adapter = new FacebookFeedListAdapter(this, entries);
		setListAdapter(adapter);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class FetchWallFeedTask extends AsyncTask<Void, Void, List<FeedEntry>> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Fetching timeline...");
		}
		
		@Override
		protected List<FeedEntry> doInBackground(Void... params) 
		{
			try
			{
				return _facebookConnectController.getFacebookApi().feedOperations().getHomeFeed();
			}
			catch(Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<FeedEntry> entries) 
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			showResult(entries);
		}
	}
}
