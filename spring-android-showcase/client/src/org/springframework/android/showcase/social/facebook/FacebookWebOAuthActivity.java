package org.springframework.android.showcase.social.facebook;

import org.springframework.android.showcase.AbstractWebViewActivity;
import org.springframework.android.showcase.R;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.facebook.api.FacebookApi;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class FacebookWebOAuthActivity extends AbstractWebViewActivity 
{
	private static final String TAG = FacebookWebOAuthActivity.class.getSimpleName();
	
	private ConnectionRepository _connectionRepository;
	
	private FacebookConnectionFactory _connectionFactory;
	
	
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
		getWebView().setWebViewClient(new FacebookOAuthWebViewClient());
		
		_connectionRepository = getApplicationContext().getConnectionRepository();
		_connectionFactory = getApplicationContext().getFacebookConnectionFactory();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		// display the Facebook authorization page
		getWebView().loadUrl(getAuthorizeUrl());
	}

	
	//***************************************
    // Private methods
    //***************************************
	private String getAuthorizeUrl() 
	{
		String redirectUri = getString(R.string.facebook_oauth_callback_url);
		String scope = getString(R.string.facebook_scope);

		// the display=touch parameter requests the mobile formatted version of the Facebook authorization page
		MultiValueMap<String, String> additionalParameters = new LinkedMultiValueMap<String, String>();
		additionalParameters.add("display", "touch");
		
		// Generate the Facebook authorization url to be used in the browser or web view
		return _connectionFactory.getOAuthOperations().buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, new OAuth2Parameters(redirectUri, scope, null, additionalParameters));
	}
	
	private void displayFacebookOptions()
	{
		Intent intent = new Intent();
		intent.setClass(this, FacebookActivity.class);
	    startActivity(intent);
    	finish();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
    private class FacebookOAuthWebViewClient extends WebViewClient 
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
        	 * 
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
	        		
	        		// create the connection and persist it to the repository
	        		AccessGrant accessGrant = new AccessGrant(accessToken);
	        		Connection<FacebookApi> connection = _connectionFactory.createConnection(accessGrant);
	        		
	        		try
	        		{
	        			_connectionRepository.addConnection(connection);
	        		}
	        		catch (DuplicateConnectionException e)
	        		{
	        			// connection already exists in repository!
	        		}
        		}
        		catch (Exception e)
        		{
        			// don't do anything if the parameters are not what is expected
        		}
        		
    			displayFacebookOptions();
        	}
        	
        	/* 
        	 * if there was an error with the oauth process, return the error description
        	 * 
        	 * The error query string will look like this:
        	 * 
        	 * ?error_reason=user_denied&error=access_denied&error_description=The+user+denied+your+request
        	 *  
        	 */
        	if (uri.getQueryParameter("error") != null) 
        	{
        		CharSequence errorReason = uri.getQueryParameter("error_description").replace("+", " ");
        		Toast.makeText(getApplicationContext(), errorReason, Toast.LENGTH_LONG).show();
        		displayFacebookOptions();
        	}
        }
    }    
}
