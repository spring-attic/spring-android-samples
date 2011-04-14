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
package org.springframework.social.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2Operations implementation that uses REST-template to make the OAuth calls.
 * @author Keith Donald
 */
public class MobileOAuth2Template implements OAuth2Operations {

	private final String clientId;
	
	private final String clientSecret;
	
	private final String accessTokenUrl;

	private final String authorizeUrl;

	private String authenticateUrl;
	
	private final RestTemplate restTemplate;
	
	public MobileOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		String clientInfo = "?response_type=token&display=touch&client_id=" + formEncode(clientId);
		this.authorizeUrl = authorizeUrl + clientInfo;
		if (authenticateUrl != null) {
			this.authenticateUrl = authenticateUrl + "?client_id=" + formEncode(clientId);
		} else {
			this.authenticateUrl = null;
		}
		this.accessTokenUrl = accessTokenUrl;
		this.restTemplate = createRestTemplate();
	}

	public final String buildAuthorizeUrl(String redirectUri, String scope, String state) {
		return buildOAuthUrl(authorizeUrl, redirectUri, scope, state);
	}
		
	public String buildAuthenticateUrl(String redirectUri, String state) {
		return authenticateUrl != null ? buildOAuthUrl(authenticateUrl, redirectUri, null, state) : buildAuthorizeUrl(redirectUri, null, state);
	}

	public final AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("code", authorizationCode);
		params.set("redirect_uri", redirectUri);
		params.set("grant_type", "authorization_code");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return postForAccessGrant(accessTokenUrl, params);
	}

	public final AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("refresh_token", refreshToken);
		if (scope != null) {
			params.set("scope", scope);
		}
		params.set("grant_type", "refresh_token");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return postForAccessGrant(accessTokenUrl, params);
	}

	// subclassing hooks
	
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(2);
		converters.add(new FormHttpMessageConverter());
		converters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	@SuppressWarnings("unchecked")
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		return extractAccessGrant(restTemplate.postForObject(accessTokenUrl, parameters, Map.class));
	}
	
	protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Integer expiresIn, Map<String, Object> result) {
		return new AccessGrant(accessToken, scope, refreshToken, expiresIn);
	}
		
	// testing hooks
	
	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	// internal helpers

	private String buildOAuthUrl(String baseOauthUrl, String redirectUri, String scope, String state) {
		StringBuilder oauthUrl = new StringBuilder(baseOauthUrl).append('&').append("redirect_uri").append('=').append(formEncode(redirectUri));
		if (scope != null) {
			oauthUrl.append('&').append("scope").append('=').append(formEncode(scope));
		}
		if (state != null) {
			oauthUrl.append('&').append("state").append('=').append(formEncode(state));	
		}
		return oauthUrl.toString();		
	}
	
	private String formEncode(String data) {
		try {
			return URLEncoder.encode(data, "UTF-8");
		}
		catch (UnsupportedEncodingException ex) {
			// should not happen, UTF-8 is always supported
			throw new IllegalStateException(ex);
		}
	}
	
	private AccessGrant extractAccessGrant(Map<String, Object> result) {
		return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), (Integer) result.get("expires_in"), result);
	}
	
}