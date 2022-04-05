/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.android.facebookclient;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Roy Clarkson
 */
public class FacebookActivity extends AbstractAsyncActivity {

	protected static final String TAG = FacebookActivity.class.getSimpleName();

	private ConnectionRepository connectionRepository;

	private FacebookConnectionFactory connectionFactory;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_activity_layout);
		this.connectionRepository = getApplicationContext().getConnectionRepository();
		this.connectionFactory = getApplicationContext().getFacebookConnectionFactory();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (isConnected()) {
			showFacebookOptions();
		} else {
			showConnectOption();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	// ***************************************
	// Private methods
	// ***************************************
	private boolean isConnected() {
		return connectionRepository.findPrimaryConnection(Facebook.class) != null;
	}

	private void disconnect() {
		this.connectionRepository.removeConnections(this.connectionFactory.getProviderId());
	}

	private void showConnectOption() {
		String[] options = { "Connect" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.facebook_activity_options_list);
		listView.setAdapter(arrayAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				switch (position) {
				case 0:
					displayFacebookAuthorization();
					break;
				default:
					break;
				}
			}
		});
	}

	private void showFacebookOptions() {
		String[] options = { "Disconnect", "Profile", "Home Feed", "Wall Post" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.facebook_activity_options_list);
		listView.setAdapter(arrayAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					disconnect();
					showConnectOption();
					break;
				case 1:
					intent = new Intent();
					intent.setClass(parentView.getContext(), FacebookProfileActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent();
					intent.setClass(parentView.getContext(), FacebookHomeFeedActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent();
					intent.setClass(parentView.getContext(), FacebookWallPostActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}

	private void displayFacebookAuthorization() {
		Intent intent = new Intent();
		intent.setClass(this, FacebookWebOAuthActivity.class);
		startActivity(intent);
		finish();
	}

}
