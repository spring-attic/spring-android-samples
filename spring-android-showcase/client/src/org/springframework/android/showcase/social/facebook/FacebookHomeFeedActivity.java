package org.springframework.android.showcase.social.facebook;

import java.util.List;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.social.facebook.api.FacebookApi;
import org.springframework.social.facebook.api.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class FacebookHomeFeedActivity extends AbstractAsyncListActivity 
{
	protected static final String TAG = FacebookHomeFeedActivity.class.getSimpleName();

	private FacebookApi _facebookApi;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_facebookApi = getApplicationContext().getConnectionRepository().findPrimaryConnectionToApi(FacebookApi.class).getApi();
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
	private void showResult(List<Post> entries)
	{
		FacebookFeedListAdapter adapter = new FacebookFeedListAdapter(this, entries);
		setListAdapter(adapter);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class FetchWallFeedTask extends AsyncTask<Void, Void, List<Post>> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Fetching home feed...");
		}
		
		@Override
		protected List<Post> doInBackground(Void... params) 
		{
			try
			{
				return _facebookApi.feedOperations().getHomeFeed();
			}
			catch(Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Post> entries) 
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			showResult(entries);
		}
	}
}
