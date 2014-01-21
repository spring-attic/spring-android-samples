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

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Entry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Feed;

/**
 * @author Roy Clarkson
 */
public class AtomFeedListAdapter extends BaseAdapter {

	private Feed feed;
	private final LayoutInflater layoutInflater;

	public AtomFeedListAdapter(Context context, Feed feed) {
		this.feed = feed;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return feed != null ? feed.getEntries().size() : 0;
	}

	public Object getItem(int position) {
		return feed.getEntries().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Entry entry = (Entry) getItem(position);

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		if (entry != null) {
			TextView textView;
			if (entry.getTitle() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_title);
				textView.setText(entry.getTitle());
			}
			if (entry.getPublished() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_date);
				textView.setText(entry.getPublished().toString());
			}
			if (entry.getSummary() != null) {
				textView = (TextView) convertView.findViewById(R.id.synd_feed_description);
				textView.setText(entry.getSummary().getValue());
			}
		}

		return convertView;
	}

}
