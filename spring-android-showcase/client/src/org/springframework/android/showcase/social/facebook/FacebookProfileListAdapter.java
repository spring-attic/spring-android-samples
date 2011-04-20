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
package org.springframework.android.showcase.social.facebook;

import org.springframework.social.facebook.api.FacebookProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class FacebookProfileListAdapter extends BaseAdapter 
{
	private FacebookProfile _facebookProfile;
	private final LayoutInflater _layoutInflater;

	public FacebookProfileListAdapter(Context context, FacebookProfile facebookProfile) 
	{
		if (facebookProfile == null)
		{
			throw new IllegalArgumentException("facebookProfile cannot be null");
		}
		
		_facebookProfile = facebookProfile;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return 3;
	}

	public String[] getItem(int position) 
	{
		String[] item = new String[2];
		
		switch(position)
		{
			case 0:
				item[0] = "Id";
				item[1] = String.valueOf(_facebookProfile.getId());
				break;
			case 1:
				item[0] = "Name";
				item[1] = _facebookProfile.getName();
				break;
			case 2:
				item[0] = "Email";
				item[1] = _facebookProfile.getEmail();
				break;
			default:
				item[0] = "";
				item[1] = "";
				break;
		}
		
		return item;
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		String[] item = getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(android.R.layout.two_line_list_item, parent, false);
		}
		
		TextView t = (TextView) view.findViewById(android.R.id.text1);
		t.setText(item[0]);
		
		t = (TextView) view.findViewById(android.R.id.text2);
		t.setText(item[1]);
		
		return view;
	}
}
