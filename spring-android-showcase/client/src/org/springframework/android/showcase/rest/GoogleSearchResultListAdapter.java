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

package org.springframework.android.showcase.rest;

import java.util.List;

import org.springframework.android.showcase.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class GoogleSearchResultListAdapter extends BaseAdapter {

	private List<GoogleSearchResult> results;
	private final LayoutInflater layoutInflater;

	public GoogleSearchResultListAdapter(Context context, List<GoogleSearchResult> results) {
		this.results = results;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return this.results != null ? this.results.size() : 0;
	}

	public GoogleSearchResult getItem(int position) {
		return this.results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.google_search_results_list_item, parent, false);
		}

		GoogleSearchResult result = getItem(position);
		if (result != null) {
			TextView t = (TextView) convertView.findViewById(R.id.title);
			t.setText(removeMarkup(result.getTitle()));

			t = (TextView) convertView.findViewById(R.id.content);
			t.setText(removeMarkup(result.getContent()));
		}

		return convertView;
	}

	private String removeMarkup(String s) {
		return s.replace("<b>", "").replace("</b>", "");
	}

}
