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

package org.springframework.android.reader;

import org.springframework.http.converter.feed.SyndFeedHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */
public class AtomSyndFeedActivity extends AbstractAsyncListActivity {

	protected static final String TAG = AtomSyndFeedActivity.class.getSimpleName();

	private SyndFeed feed;

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
		if (feed == null) {
			return;
		}

		SyndEntry entry = (SyndEntry) feed.getEntries().get(position);
		String link = entry.getLink();
		Log.i(TAG, link);
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(link));
		this.startActivity(intent);
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void refreshAtomFeed(SyndFeed feed) {
		this.feed = feed;

		if (feed == null) {
			return;
		}

		setTitle(feed.getTitle());

		SyndFeedListAdapter adapter = new SyndFeedListAdapter(this, feed);
		setListAdapter(adapter);
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadAtomFeedTask extends AsyncTask<Void, Void, SyndFeed> {

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected SyndFeed doInBackground(Void... params) {
			try {
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Add the SyndFeedHttpMessageConverter to the RestTemplate instance, since it is not automatically available
				restTemplate.getMessageConverters().add(new SyndFeedHttpMessageConverter());

				// The URL for making the request
				final String url = getString(R.string.atom_feed_url);

				// Initiate the request and return the results.
				return restTemplate.getForObject(url, SyndFeed.class);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(SyndFeed feed) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();

			// return the feed list
			refreshAtomFeed(feed);
		}

	}

}
