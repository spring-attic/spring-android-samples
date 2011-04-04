package org.springframework.android.showcase.social.twitter;

import org.springframework.android.showcase.AbstractWebViewActivity;

import android.os.Bundle;

public class TwitterWebOAuthActivity extends AbstractWebViewActivity 
{
//	private static final String TAG = TwitterOAuthActivity.class.getSimpleName();
	
	
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
		
		if (getIntent().hasExtra("authUrl"))
		{
			String authUrl = getIntent().getExtras().getString("authUrl");
			
			if (authUrl != null)
			{  
				getWebView().loadUrl(authUrl);
			}
		}
	}
}
