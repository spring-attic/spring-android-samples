/*
 * Copyright 2010-2014 the original author or authors.
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

package org.springframework.android.reader;

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
public class RssChannelListAdapter extends BaseAdapter {

	private Channel channel;
	private final LayoutInflater layoutInflater;

	public RssChannelListAdapter(Context context, Channel channel) {
		this.channel = channel;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return channel != null ? channel.getItems().size() : 0;
	}

	public Object getItem(int position) {
		return channel.getItems().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Item item = (Item) getItem(position);

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		if (item != null) {
			TextView textView;
			if (item.getTitle() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_title);
				textView.setText(item.getTitle());
			}
			if (item.getPubDate() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_date);
				textView.setText(item.getPubDate().toString());
			}
			if (item.getDescription() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_description);
				textView.setText(item.getDescription().getValue());
			}
		}

		return convertView;
	}

}
