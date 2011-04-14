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
import org.springframework.social.connect.DuplicateServiceProviderConnectionException;
import org.springframework.social.connect.ServiceProviderConnection;
import org.springframework.social.connect.sqlite.SqliteServiceProviderConnectionRepository;
import org.springframework.social.connect.sqlite.support.SqliteServiceProviderConnectionRepositoryHelper;
import org.springframework.social.connect.support.MapServiceProviderConnectionFactoryRegistry;
import org.springframework.social.facebook.FacebookApi;
import org.springframework.social.facebook.connect.FacebookMobileServiceProviderConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Roy Clarkson
 */
public class FacebookConnectController 
{
	private static final String PROVIDER_ID = "facebook";
	
	private Context _context;
	private MapServiceProviderConnectionFactoryRegistry _connectionFactoryRegistry;
	private FacebookMobileServiceProviderConnectionFactory _connectionFactory;
	private SQLiteOpenHelper _repositoryHelper;
	private SqliteServiceProviderConnectionRepository _connectionRepository;
	
	
	//***************************************
    // Constructors
    //***************************************
	public FacebookConnectController(Context context)
	{
		_context = context;
		_connectionFactoryRegistry = new MapServiceProviderConnectionFactoryRegistry();
		_connectionFactory = new FacebookMobileServiceProviderConnectionFactory(getAppId(), getAppSecret());
		_connectionFactoryRegistry.addConnectionFactory(_connectionFactory);
		_repositoryHelper = new SqliteServiceProviderConnectionRepositoryHelper(_context);
		_connectionRepository = new SqliteServiceProviderConnectionRepository(getLocalUserId(), _repositoryHelper, _connectionFactoryRegistry, Encryptors.noOpText());
	}
	
	
	//***************************************
    // Accessor methods
    //***************************************
	private String getLocalUserId()
	{
		return _context.getString(R.string.local_user_id);
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
		
	public FacebookApi getFacebookApi() 
	{
		List<ServiceProviderConnection<?>> connections = _connectionRepository.findConnectionsToProvider(PROVIDER_ID);
		ServiceProviderConnection<FacebookApi> facebook = (ServiceProviderConnection<FacebookApi>) connections.get(0);
		return facebook.getServiceApi();
	}
	
	public boolean isConnected() 
	{
		return !_connectionRepository.findConnectionsToProvider(PROVIDER_ID).isEmpty();
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public String getAuthorizeUrl() 
	{
		// Generate the Facebook authorization url to be used in the browser or web view
		return _connectionFactory.getOAuthOperations().buildAuthorizeUrl(getOAuthCallbackUrl(), getScope(), null);
	}
		
	public void connect(String accessToken)
	{
		AccessGrant accessGrant = new AccessGrant(accessToken, null, null, null);
		ServiceProviderConnection<FacebookApi> connection = _connectionFactory.createConnection(accessGrant);
		
		try
		{
			_connectionRepository.addConnection(connection);
		}
		catch (DuplicateServiceProviderConnectionException e)
		{
			// connection already exists in repository!
		}
	}
	
	public void disconnect()
	{
		_connectionRepository.removeConnectionsToProvider(PROVIDER_ID);
	}
}
