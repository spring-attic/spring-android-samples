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

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class SyndFeedListAdapter extends BaseAdapter {

	private SyndFeed syndFeed;
	private final LayoutInflater layoutInflater;

	public SyndFeedListAdapter(Context context, SyndFeed feed) {
		this.syndFeed = feed;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return syndFeed != null ? syndFeed.getEntries().size() : 0;
	}

	public SyndEntry getItem(int position) {
		return (SyndEntry) syndFeed.getEntries().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		SyndEntry syndEntry = getItem(position);

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		if (syndEntry != null) {
			TextView textView;
			if (syndEntry.getTitle() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_title);
				textView.setText(syndEntry.getTitle());
			}
			if (syndEntry.getPublishedDate() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_date);
				textView.setText(syndEntry.getPublishedDate().toString());
			}
			if (syndEntry.getDescription() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_description);
				textView.setText(syndEntry.getDescription().getValue());
			}
		}

		return convertView;
	}

}
