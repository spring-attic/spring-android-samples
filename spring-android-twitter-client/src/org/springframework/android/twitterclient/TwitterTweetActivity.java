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

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Roy Clarkson
 */
public class TwitterTweetActivity extends AbstractAsyncActivity {

	protected static final String TAG = TwitterTweetActivity.class.getSimpleName();

	private Twitter twitter;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_tweet_activity_layout);
		this.twitter = getApplicationContext().getConnectionRepository().findPrimaryConnection(Twitter.class).getApi();

		// Initiate the POST request when the button is clicked
		final Button button = (Button) findViewById(R.id.button_tweet);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// hide the soft keypad
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				EditText editText = (EditText) findViewById(R.id.edit_text_tweet);
				inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				new PostTweetTask().execute();
			}
		});
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(String result) {
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostTweetTask extends AsyncTask<Void, Void, String> {

		private String tweetText;

		@Override
		protected void onPreExecute() {
			showProgressDialog("Updating Status...");

			// retrieve the tweet text from the EditText field
			EditText editText = (EditText) findViewById(R.id.edit_text_tweet);
			tweetText = editText.getText().toString();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				twitter.timelineOperations().updateStatus(tweetText);
				return "Status updated";
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "An error occurred. See the log for details";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}

	}

}
