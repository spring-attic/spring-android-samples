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
package org.springframework.android.showcase.social.facebook;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.social.facebook.api.FacebookApi;
import org.springframework.social.facebook.api.FacebookProfile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class FacebookProfileActivity extends AbstractAsyncListActivity 
{
	protected static final String TAG = FacebookProfileActivity.class.getSimpleName();

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
		
		new FetchProfileTask().execute();
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showResult(FacebookProfile facebookProfile)
	{
		if (facebookProfile != null)
		{
			FacebookProfileListAdapter adapter = new FacebookProfileListAdapter(this, facebookProfile);
			setListAdapter(adapter);
		}
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class FetchProfileTask extends AsyncTask<Void, Void, FacebookProfile> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Fetching profile...");
		}
		
		@Override
		protected FacebookProfile doInBackground(Void... params) 
		{
			try
			{
				return _facebookApi.userOperations().getUserProfile();
			}
			catch(Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(FacebookProfile profile) 
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			showResult(profile);
		}
	}
}
