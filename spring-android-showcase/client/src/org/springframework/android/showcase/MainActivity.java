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
public class MainActivity extends ListActivity
{   
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		String[] options = getResources().getStringArray(R.array.main_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		setListAdapter(arrayAdapter);
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		Intent intent = new Intent();
		
		switch(position) 
		{
			case 0:
				intent.setClass(this, GoogleSearchActivity.class);
				startActivity(intent);
				break;
	      	case 1:
			    intent.setClass(this, HttpGetActivity.class);
			    startActivity(intent);
	      		break;
	      	case 2:
	      		intent.setClass(this, HttpGetParametersActivity.class);
			    startActivity(intent);
	      		break;
	      	case 3:
			    intent.setClass(this, HttpPostActivity.class);
			    startActivity(intent);
	      		break;
	      	case 4:
	      		intent.setClass(this, RssActivity.class);
	      		startActivity(intent);
	      		break;
	      	case 5:
	      		intent.setClass(this, AtomActivity.class);
	      		startActivity(intent);
	      		break;
	      	case 6:
	      		intent.setClass(this, TwitterActivity.class);
	      		startActivity(intent);
	      		break;
	      	case 7:
	      		intent.setClass(this, FacebookActivity.class);
	      		startActivity(intent);
	      		break;
	      	default:
	      		break;
		}
	}
}
