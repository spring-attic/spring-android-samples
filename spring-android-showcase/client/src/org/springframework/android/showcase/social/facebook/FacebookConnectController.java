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
import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.FacebookApi;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Roy Clarkson
 */
public class FacebookConnectController 
{
	private Context _context;
	private ConnectionFactoryRegistry _connectionFactoryRegistry;
	private FacebookConnectionFactory _connectionFactory;
	private SQLiteOpenHelper _repositoryHelper;
	private SQLiteConnectionRepository _connectionRepository;
	
	
	//***************************************
    // Constructors
    //***************************************
	public FacebookConnectController(Context context)
	{
		_context = context;
		_connectionFactoryRegistry = new ConnectionFactoryRegistry();
		_connectionFactory = new FacebookConnectionFactory(getAppId(), getAppSecret());
		_connectionFactoryRegistry.addConnectionFactory(_connectionFactory);
		_repositoryHelper = new SQLiteConnectionRepositoryHelper(_context);
		_connectionRepository = new SQLiteConnectionRepository(getLocalUserId(), _repositoryHelper, _connectionFactoryRegistry, AndroidEncryptors.text("password", "5c0744940b5c369b"));
	}
	
	
	//***************************************
    // Accessor methods
    //***************************************
	private String getLocalUserId()
	{
		return _context.getString(R.string.local_user_id);
	}
	
	private String getProviderId()
	{
		return _context.getString(R.string.facebook_provider_id);
	}

	private String getAppId()
	{
		return _context.getString(R.string.facebook_app_id);
	}
	
	private String getAppSecret()
	{
		return _context.getString(R.string.facebook_app_secret);
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
	@SuppressWarnings("unchecked")
	public FacebookApi getFacebookApi() 
	{
		List<Connection<?>> connections = _connectionRepository.findConnectionsToProvider(getProviderId());
		Connection<FacebookApi> facebook = (Connection<FacebookApi>) connections.get(0);
		return facebook.getApi();
	}
	
	public boolean isConnected() 
	{
		return !_connectionRepository.findConnectionsToProvider(getProviderId()).isEmpty();
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
