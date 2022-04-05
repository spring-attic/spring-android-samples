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

package org.springframework.android.twittersearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Roy Clarkson
 */
public class TwitterSearchActivity extends Activity {

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_search_activity_layout);

		final Button buttonJson = (Button) findViewById(R.id.search_button);
		buttonJson.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				search();
			}
		});
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void search() {
		final EditText search_value = (EditText) findViewById(R.id.search_value);
		Intent intent = new Intent(this.getApplicationContext(), TwitterSearchResultsActivity.class);
		String searchValue = search_value.getText().toString();
		intent.putExtra("org.springframework.android.showcase.TwitterSearchResultsActivity.search", searchValue);
		startActivity(intent);
	}

}
