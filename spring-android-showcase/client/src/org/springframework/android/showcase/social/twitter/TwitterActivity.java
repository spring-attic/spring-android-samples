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
package org.springframework.android.showcase.social.twitter;

import org.springframework.android.showcase.AbstractAsyncActivity;
import org.springframework.android.showcase.R;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Roy Clarkson
 */
public class TwitterActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = TwitterActivity.class.getSimpleName();
	
	private TwitterConnectController _twitterConnectController;
	

	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_twitterConnectController = new TwitterConnectController(this);
		
		setContentView(R.layout.twitter_activity_layout);
	}
	
	@Override
	public void onStart() 
	{
		super.onStart();
		
		if (_twitterConnectController.isConnected())
		{
			showTwitterOptions();
		}
		else
		{
			showConnectOption();
		}
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		if (!_twitterConnectController.isConnected())
		{
			Uri uri = getIntent().getData();
			if (_twitterConnectController.isCallbackUrl(uri)) 
			{
				String oauthVerifier = uri.getQueryParameter("oauth_verifier");
				new TwitterPostConnectTask().execute(oauthVerifier);
			}
		}
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showConnectOption()
	{
		String[] options = {"Connect"};
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.twitter_activity_options_list);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(
				new AdapterView.OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) 
					{
						switch(position)
						{
							case 0:
								new TwitterPreConnectTask().execute();
								break;
							default:
								break;
						}
					}
				}
			);
	}
	
	private void showTwitterOptions()
	{
		String[] options = {"Disconnect", "View Profile", "Timeline", "Tweet", "Direct Message"};
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.twitter_activity_options_list);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(
				new AdapterView.OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) 
					{
						Intent intent;
						switch(position)
						{
							case 0:
								disconnect();
								showConnectOption();
								break;
							case 1:
								intent = new Intent();
								intent.setClass(parentView.getContext(), TwitterProfileActivity.class);
							    startActivity(intent);
								break;
							case 2:
								intent = new Intent();
								intent.setClass(parentView.getContext(), TwitterTimelineActivity.class);
							    startActivity(intent);
								break;
							case 3:
								intent = new Intent();
								intent.setClass(parentView.getContext(), TwitterTweetActivity.class);
							    startActivity(intent);
								break;
							case 4:
								intent = new Intent();
								intent.setClass(parentView.getContext(), TwitterDirectMessageActivity.class);
							    startActivity(intent);
								break;
							default:
								break;
						}
					}
				}
			);
	}
	
	private void displayTwitterAuthorization(String authUrl)
	{		
		Intent intent = new Intent();
		intent.setClass(this, TwitterOAuthActivity.class);
		intent.putExtra("authUrl", authUrl);
		startActivity(intent);
		finish();
	}
	
	private void disconnect()
	{
		_twitterConnectController.disconnect();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class TwitterPreConnectTask extends AsyncTask<Void, Void, String> 
	{		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Initializing OAuth Connection...");
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{			
			return _twitterConnectController.getTwitterAuthorizeUrl();
		}
		
		@Override
		protected void onPostExecute(String authUrl)
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			displayTwitterAuthorization(authUrl);
		}
	}
	
	private class TwitterPostConnectTask extends AsyncTask<String, Void, Void> 
	{		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Finalizing OAuth Connection...");
		}
		
		@Override
		protected Void doInBackground(String... params) 
		{
			if (params.length <= 0)
			{
				return null;
			}
			
			final String verifier = params[0];
			
			_twitterConnectController.updateTwitterAccessToken(verifier);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v)
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			showTwitterOptions();
		}
	}
}
