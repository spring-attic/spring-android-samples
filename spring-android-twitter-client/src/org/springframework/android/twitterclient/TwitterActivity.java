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

package org.springframework.android.twitterclient;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Roy Clarkson
 */
public class TwitterActivity extends AbstractAsyncActivity {

	protected static final String TAG = TwitterActivity.class.getSimpleName();

	private ConnectionRepository connectionRepository;

	private TwitterConnectionFactory connectionFactory;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_activity_layout);
		this.connectionRepository = getApplicationContext().getConnectionRepository();
		this.connectionFactory = getApplicationContext().getTwitterConnectionFactory();
	}

	@Override
	public void onStart() {
		super.onStart();

		if (isConnected()) {
			showTwitterOptions();
		} else {
			showConnectOption();
		}
	}

	// ***************************************
	// Private methods
	// ***************************************
	private boolean isConnected() {
		return this.connectionRepository.findPrimaryConnection(Twitter.class) != null;
	}

	private void disconnect() {
		this.connectionRepository.removeConnections(this.connectionFactory.getProviderId());
	}

	private void showConnectOption() {
		String[] options = { "Connect" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.twitter_activity_options_list);
		listView.setAdapter(arrayAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				switch (position) {
				case 0:
					displayTwitterAuthorization();
					break;
				default:
					break;
				}
			}
		});
	}

	private void showTwitterOptions() {
		String[] options = { "Disconnect", "View Profile", "Timeline", "Tweet", "Direct Message" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.twitter_activity_options_list);
		listView.setAdapter(arrayAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				switch (position) {
				case 0:
					disconnect();
					showConnectOption();
					break;
				case 1:
					startActivity(new Intent(parentView.getContext(), TwitterProfileActivity.class));
					break;
				case 2:
					startActivity(new Intent(parentView.getContext(), TwitterTimelineActivity.class));
					break;
				case 3:
					startActivity(new Intent(parentView.getContext(), TwitterTweetActivity.class));
					break;
				case 4:
					startActivity(new Intent(parentView.getContext(), TwitterDirectMessageActivity.class));
					break;
				default:
					break;
				}
			}
		});
	}

	private void displayTwitterAuthorization() {
		startActivity(new Intent(this, TwitterWebOAuthActivity.class));
		finish();
	}

}
