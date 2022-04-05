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

package org.springframework.android.facebookclient;

import java.util.List;

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
public class FacebookFeedListAdapter extends BaseAdapter {
	private List<Post> entries;
	private final LayoutInflater layoutInflater;

	public FacebookFeedListAdapter(Context context, List<Post> entries) {
		this.entries = entries;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return entries == null ? 0 : entries.size();
	}

	public Post getItem(int position) {
		return entries.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Post entry = getItem(position);
		View view = convertView;

		if (view == null) {
			view = layoutInflater.inflate(R.layout.facebook_feed_list_item, parent, false);
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
