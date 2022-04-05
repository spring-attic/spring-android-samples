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

import org.springframework.social.facebook.api.Facebook;

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
public class FacebookWallPostActivity extends AbstractAsyncActivity {

	protected static final String TAG = FacebookWallPostActivity.class.getSimpleName();

	private Facebook facebook;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_wall_post_activity_layout);
		this.facebook = getApplicationContext().getConnectionRepository().findPrimaryConnection(Facebook.class)
				.getApi();

		// Initiate the POST request when the button is clicked
		final Button button = (Button) findViewById(R.id.submit_button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// hide the soft keypad
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				EditText editText = (EditText) findViewById(R.id.wall_post_text);
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

		private String wallPostText;

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showProgressDialog("Posting to Wall...");

			// retrieve the text from the EditText field
			EditText editText = (EditText) findViewById(R.id.wall_post_text);
			this.wallPostText = editText.getText().toString();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				facebook.feedOperations().updateStatus(wallPostText);
				return "Status updated";
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "An error occurred. See the log for details";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			showResult(result);
		}

	}

}
