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

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Channel;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Item;

/**
 * @author Roy Clarkson
 */
public class RssChannelListAdapter extends BaseAdapter 
{
	private Channel _channel;
	private final LayoutInflater _layoutInflater;

	public RssChannelListAdapter(Context context, Channel channel) 
	{
		_channel = channel;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return _channel.getItems().size();
	}

	public Object getItem(int position) 
	{
		return _channel.getItems().get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Item item = (Item) getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		TextView t = (TextView) view.findViewById(R.id.synd_feed_title);
		t.setText(item.getTitle());
		
		t = (TextView) view.findViewById(R.id.synd_feed_date);
		t.setText(item.getPubDate().toString());
		
		t = (TextView) view.findViewById(R.id.synd_feed_description);
		t.setText(item.getDescription().getValue());

		return view;
	}
}
