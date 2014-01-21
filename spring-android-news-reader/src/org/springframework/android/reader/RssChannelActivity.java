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

import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Channel;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Item;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */
public class RssChannelActivity extends AbstractAsyncListActivity {

	protected static final String TAG = RssChannelActivity.class.getSimpleName();

	private Channel channel;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(null);

		// initiate the asynchronous network request
		new DownloadRssFeedTask().execute();
	}

	// ***************************************
	// ListActivity methods
	// ***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (channel == null) {
			return;
		}

		// Open the selected RSS item in the browser
		Item item = (Item) channel.getItems().get(position);
		String uri = item.getUri();
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
		this.startActivity(intent);
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void refreshRssFeed(Channel channel) {
		this.channel = channel;

		if (channel == null) {
			return;
		}

		setTitle(channel.getTitle());
		RssChannelListAdapter adapter = new RssChannelListAdapter(this, channel);
		setListAdapter(adapter);
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadRssFeedTask extends AsyncTask<Void, Void, Channel> {

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected Channel doInBackground(Void... params) {
			try {
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Configure the RSS message converter.
				RssChannelHttpMessageConverter rssChannelConverter = new RssChannelHttpMessageConverter();
				rssChannelConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_XML));

				// Add the RSS message converter to the RestTemplate instance
				restTemplate.getMessageConverters().add(rssChannelConverter);

				// The URL for making the request
				final String url = getString(R.string.rss_feed_url);

				// Initiate the request and return the results
				return restTemplate.getForObject(url, Channel.class);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Channel channel) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();

			// return the list of states
			refreshRssFeed(channel);
		}

	}

}
