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

import java.util.List;

import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Entry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Feed;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Link;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */
public class AtomFeedActivity extends AbstractAsyncListActivity {

	protected static final String TAG = AtomFeedActivity.class.getSimpleName();

	private Feed feed;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(null);

		// initiate the asynchronous network request
		new DownloadAtomFeedTask().execute();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	// ***************************************
	// ListActivity methods
	// ***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Entry entry = (Entry) feed.getEntries().get(position);
		List<?> alternateLinks = entry.getAlternateLinks();

		if (alternateLinks.size() > 0) {
			Link link = (Link) alternateLinks.get(0);
			Log.i(TAG, link.getHref());
			Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(link.getHref()));
			this.startActivity(intent);
		}
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void refreshAtomFeed(Feed feed) {
		this.feed = feed;

		if (feed == null) {
			return;
		}

		setTitle(feed.getTitle());
		AtomFeedListAdapter adapter = new AtomFeedListAdapter(this, feed);
		setListAdapter(adapter);
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadAtomFeedTask extends AsyncTask<Void, Void, Feed> {

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected Feed doInBackground(Void... params) {
			try {
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Add the ATOM message converter to the RestTemplate instance, since it is not automatically available
				restTemplate.getMessageConverters().add(new AtomFeedHttpMessageConverter());

				// The URL for making the request
				final String url = getString(R.string.atom_feed_url);

				// Initiate the request and return the results
				return restTemplate.getForObject(url, Feed.class);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Feed feed) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();

			// return the feed list
			refreshAtomFeed(feed);
		}

	}

}
