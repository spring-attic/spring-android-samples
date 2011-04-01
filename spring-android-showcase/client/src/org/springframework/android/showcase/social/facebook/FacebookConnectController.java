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

import java.util.List;

import org.springframework.android.showcase.R;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ServiceProviderConnection;
import org.springframework.social.connect.sqlite.SqliteConnectionRepository;
import org.springframework.social.facebook.FacebookApi;
import org.springframework.social.facebook.connect.FacebookMobileServiceProvider;
import org.springframework.social.oauth2.AccessGrant;

import android.content.Context;

/**
 * @author Roy Clarkson
 */
public class FacebookConnectController 
{
	private static FacebookMobileServiceProvider _sharedFacebookServiceProvider;
	
	private final Context _context;
	
	
	//***************************************
    // Constructors
    //***************************************
	public FacebookConnectController(Context context)
	{
		_context = context;
	}
	
	
	//***************************************
    // Accessor methods
    //***************************************
	private String getDefaultConnectAccountId()
	{
		return _context.getString(R.string.default_connect_account_id);
	}

	private String getFacebookAppId()
	{
		return _context.getString(R.string.facebook_app_id);
	}
	
	private String getFacebookAppSecret()
	{
		return _context.getString(R.string.facebook_app_secret);
	}
		
	private String getOAuthCallbackUrl()
	{
		return _context.getString(R.string.facebook_oauth_callback_url);
	}	
	
	public FacebookMobileServiceProvider getFacebookServiceProvider() 
	{
		if (_sharedFacebookServiceProvider == null) 
		{
			_sharedFacebookServiceProvider = new FacebookMobileServiceProvider(
					getFacebookAppId(), 
					getFacebookAppSecret(),
					new SqliteConnectionRepository(_context, Encryptors.noOpText()));
		}
		
		return _sharedFacebookServiceProvider;
	}
	
	public FacebookApi getFacebookApi() 
	{
		if (getFacebookServiceProvider().isConnected(getDefaultConnectAccountId()))
		{
			return getFacebookServiceProvider().getConnections(getDefaultConnectAccountId()).get(0).getServiceApi();
		}

		return null;
	}
	
	public boolean isConnected() 
	{
		return getFacebookServiceProvider().isConnected(getDefaultConnectAccountId());
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public String getAuthorizeUrl() 
	{
		// Generate the Facebook authorization url to be used in the browser or web view
		return getFacebookServiceProvider().getOAuthOperations().buildAuthorizeUrl(getOAuthCallbackUrl(), "publish_stream,offline_access,read_stream,user_about_me");
	}
		
	public void connect(String accessToken)
	{
		// persist the new connection
		getFacebookServiceProvider().connect(getDefaultConnectAccountId(), new AccessGrant(accessToken));
	}
	
	public void disconnect()
	{
		if (getFacebookServiceProvider().isConnected(getDefaultConnectAccountId()))
		{
			// get all the Facebook connections for our default Spring Social account id
			List<ServiceProviderConnection<FacebookApi>> facebookConnections = getFacebookServiceProvider().getConnections(getDefaultConnectAccountId());
			
			// we are only working with a single Facebook connection, so get the first one from the list
			ServiceProviderConnection<FacebookApi> defaultFacebookConnection = facebookConnections.get(0);
			
			// disconnect will remove the record from the local database managed by Spring Social
			defaultFacebookConnection.disconnect();
		}
	}
}
