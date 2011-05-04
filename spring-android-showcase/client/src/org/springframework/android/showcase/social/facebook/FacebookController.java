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

import android.content.Context;

/**
 * @author Roy Clarkson
 */
public class FacebookController 
{
	private Context _context;
	private FacebookConnectionFactory _connectionFactory;
	private ConnectionRepository _connectionRepository;
	
	
	//***************************************
    // Constructors
    //***************************************
	public FacebookController(Context context, FacebookConnectionFactory connectionFactory, ConnectionRepository connectionRepository)
	{
		_context = context;
		_connectionFactory = connectionFactory;
		_connectionRepository = connectionRepository;
	}
	
	
	//***************************************
    // Accessor methods
    //***************************************
	private String getProviderId()
	{
		return _connectionFactory.getProviderId();
	}
	
	private String getOAuthCallbackUrl()
	{
		return _context.getString(R.string.facebook_oauth_callback_url);
	}
	
	private String getScope() 
	{
		return _context.getString(R.string.facebook_scope);
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public FacebookApi getFacebookApi() 
	{
		return _connectionRepository.findPrimaryConnectionToApi(FacebookApi.class).getApi();
	}
	
	public boolean isConnected() 
	{
		return _connectionRepository.findPrimaryConnectionToApi(FacebookApi.class) != null;
	}
	
	public String getAuthorizeUrl() 
	{
		// Generate the Facebook authorization url to be used in the browser or web view
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("display", "touch");
		return _connectionFactory.getOAuthOperations().buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, new OAuth2Parameters(getOAuthCallbackUrl(), getScope(), null, params));
	}
		
	public void connect(String accessToken)
	{
		AccessGrant accessGrant = new AccessGrant(accessToken, null, null, null);
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
	
	public void disconnect()
	{
		_connectionRepository.removeConnectionsToProvider(getProviderId());
	}
}
