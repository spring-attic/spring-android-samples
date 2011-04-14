package org.springframework.android.showcase.social.facebook;

import org.springframework.android.showcase.AbstractWebViewActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FacebookWebOAuthActivity extends AbstractWebViewActivity 
{
//	private static final String TAG = FacebookOAuthActivity.class.getSimpleName();
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		// Facebook uses javascript to redirect to the success page
		getWebView().getSettings().setJavaScriptEnabled(true);
		
		// Using a custom web view client to capture the access token
		getWebView().setWebViewClient(new CustomWebViewClient());
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		// The authUrl is passed from FacebookActivity
		if (getIntent().hasExtra("authUrl"))
		{
			String authUrl = getIntent().getStringExtra("authUrl");
		
			if (authUrl != null)
			{  
				getIntent().removeExtra("authUrl");
				getWebView().loadUrl(authUrl);
			}
		}
	}
	
	
	//***************************************
    // Private classes
    //***************************************
    private class CustomWebViewClient extends WebViewClient 
    {
        /*
         * The WebViewClient has another method called shouldOverridUrlLoading
         * which does not capture the javascript redirect to the success page.
         * So we're using onPageStarted to capture the url.
         */
        @Override
        public  void onPageStarted(WebView view, String url, Bitmap favicon) 
        {
        	// parse the captured url
        	Uri uri = Uri.parse(url);
        	
        	Log.d(TAG, url);
        	
        	/*
        	 * The access token is returned in the URI fragment of the URL
        	 * See the Desktop Apps section all the way at the bottom of this link:
        	 * http://developers.facebook.com/docs/authentication/
        	 * 
        	 * The fragment will be formatted like this:
        	 * 
        	 * #access_token=A&expires_in=0
        	 */
        	String uriFragment = uri.getFragment();
        	
        	// confirm we have the fragment, and it has an access_token parameter
        	if (uriFragment != null && uriFragment.startsWith("access_token="))
        	{
        		/*
        		 *  The fragment also contains an "expires_in" parameter. In this example 
        		 *  we requested the offline_access permission, which basically means the
        		 *  access will not expire, so we're ignoring it here 
        		 */
        		try
        		{
        			// split to get the two different parameters
	        		String[] params = uriFragment.split("&");
	        		
	        		// split to get the access token parameter and value
	        		String[] accessTokenParam = params[0].split("=");
	        		
	        		// get the access token value
	        		String accessToken = accessTokenParam[1];
	        		
	        		// return the access token value to the previous activity
					Intent intent = new Intent();
					intent.setClass(view.getContext(), FacebookActivity.class);
					intent.putExtra("accessToken", accessToken);
				    startActivity(intent);
		        	finish();
        		}
        		catch (Exception e)
        		{
        			// don't do anything if the parameters are not what is expected
        			return;
        		}
        	}
        }
    }    
}
