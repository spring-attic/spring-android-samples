package org.springframework.android.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class TwitterOauthActivity extends Activity 
{
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.twitter_oauth_activity_layout);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		String authUrl = getIntent().getExtras().getString("authUrl");
		
		if (authUrl != null)
		{
			WebView webView = (WebView) findViewById(R.id.web_view);  
			webView.loadUrl(authUrl);
		}
	}
}
