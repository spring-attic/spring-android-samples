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
package org.springframework.android.showcase;

import org.springframework.android.showcase.rest.GoogleSearchActivity;
import org.springframework.android.showcase.rest.HttpGetActivity;
import org.springframework.android.showcase.rest.HttpGetGzipActivity;
import org.springframework.android.showcase.rest.HttpGetParametersActivity;
import org.springframework.android.showcase.rest.HttpPostActivity;
import org.springframework.android.showcase.rest.rome.AtomActivity;
import org.springframework.android.showcase.rest.rome.RssActivity;
import org.springframework.android.showcase.social.facebook.FacebookActivity;
import org.springframework.android.showcase.social.twitter.TwitterActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class MainActivity extends ListActivity { 
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] options = getResources().getStringArray(R.array.main_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		setListAdapter(arrayAdapter);
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {		
		switch (position) {
		case 0:
			startActivity(new Intent(this, GoogleSearchActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, HttpGetActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, HttpGetParametersActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, HttpGetGzipActivity.class));
			break;
		case 4:
			startActivity(new Intent(this, HttpPostActivity.class));
			break;
		case 5:
			startActivity(new Intent(this, RssActivity.class));
			break;
		case 6:
			startActivity(new Intent(this, AtomActivity.class));
			break;
		case 7:
			startActivity(new Intent(this, TwitterActivity.class));
			break;
		case 8:
			startActivity(new Intent(this, FacebookActivity.class));
			break;
		default:
			break;
		}
	}
	
}
