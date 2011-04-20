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

import java.util.List;

import org.springframework.android.showcase.R;
import org.springframework.social.facebook.api.Post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class FacebookFeedListAdapter extends BaseAdapter 
{
	private List<Post> _entries;
	private final LayoutInflater _layoutInflater;

	public FacebookFeedListAdapter(Context context, List<Post> entries) 
	{
		_entries = entries;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return _entries == null ? 0 : _entries.size();
	}

	public Post getItem(int position) 
	{
		return _entries.get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		Post entry = getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(R.layout.facebook_feed_list_item, parent, false);
		}
		
		TextView t = (TextView) view.findViewById(R.id.from_name);
		t.setText(entry.getFrom().getName());
		
		t = (TextView) view.findViewById(R.id.updated_time);
		t.setText(entry.getUpdatedTime().toString());
		
		t = (TextView) view.findViewById(R.id.message);
		t.setText(entry.getMessage());
		
		return view;
	}
}
