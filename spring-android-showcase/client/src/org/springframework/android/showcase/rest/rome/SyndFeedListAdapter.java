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
package org.springframework.android.showcase.rest.rome;

import org.springframework.android.showcase.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class SyndFeedListAdapter extends BaseAdapter 
{
	private SyndFeed _syndFeed;
	private final LayoutInflater _layoutInflater;

	public SyndFeedListAdapter(Context context, SyndFeed feed) 
	{
		_syndFeed = feed;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return _syndFeed.getEntries().size();
	}

	public SyndEntry getItem(int position) 
	{
		return (SyndEntry) _syndFeed.getEntries().get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		SyndEntry syndEntry = getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		TextView t = (TextView) view.findViewById(R.id.synd_feed_title);
		t.setText(syndEntry.getTitle());
		
		t = (TextView) view.findViewById(R.id.synd_feed_date);
		t.setText(syndEntry.getPublishedDate().toString());
		
		t = (TextView) view.findViewById(R.id.synd_feed_description);
		t.setText(syndEntry.getDescription().getValue());

		return view;
	}
}
