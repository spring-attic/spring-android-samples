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

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class TwitterProfileActivity extends AbstractAsyncListActivity {

	protected static final String TAG = TwitterProfileActivity.class.getSimpleName();

	private Twitter twitter;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.twitter = getApplicationContext().getConnectionRepository().findPrimaryConnection(Twitter.class).getApi();
	}

	@Override
	public void onStart() {
		super.onStart();
		new FetchProfileTask().execute();
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(TwitterProfile twitterProfile) {
		if (twitterProfile != null) {
			TwitterProfileListAdapter adapter = new TwitterProfileListAdapter(this, twitterProfile);
			setListAdapter(adapter);
		}
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class FetchProfileTask extends AsyncTask<Void, Void, TwitterProfile> {

		@Override
		protected void onPreExecute() {
			showProgressDialog("Fetching profile...");
		}

		@Override
		protected TwitterProfile doInBackground(Void... params) {
			try {
				return twitter.userOperations().getUserProfile();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(TwitterProfile profile) {
			dismissProgressDialog();
			showResult(profile);
		}

	}

}
