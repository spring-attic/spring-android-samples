/*
 * Copyright 2010 the original author or authors.
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
package org.springframework.social.facebook.connect;

import org.springframework.social.connect.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.connect.support.ConnectionRepository;
import org.springframework.social.facebook.FacebookApi;
import org.springframework.social.facebook.FacebookTemplate;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Facebook ServiceProvider implementation for Android.
 * @author Roy Clarkson
 */
public final class FacebookMobileServiceProvider extends AbstractOAuth2ServiceProvider<FacebookApi> {

	private final String appId;
	
	private final String appSecret;
	
	public FacebookMobileServiceProvider(String appId, String appSecret, ConnectionRepository connectionRepository) {
		super("facebook", connectionRepository, new OAuth2Template(appId, appSecret,
				"https://graph.facebook.com/oauth/authorize?client_id={client_id}&redirect_uri={redirect_uri}&response_type=token&scope={scope}&display=touch",
				"https://graph.facebook.com/oauth/access_token"));
		this.appId = appId;
		this.appSecret = appSecret;
	}

	@Override
	protected FacebookApi getApi(String accessToken) {
		return new FacebookTemplate(accessToken);
	}
}