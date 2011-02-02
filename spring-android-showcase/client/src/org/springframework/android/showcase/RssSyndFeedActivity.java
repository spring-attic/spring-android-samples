package org.springframework.android.showcase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
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

public class RssSyndFeedActivity extends AbstractAsyncListActivity 
{
	protected String TAG = "RssSyndFeedActivity";
	
	private SyndFeed _feed;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setTitle(null);
		
		// initiate the asynchronous network request
		new DownloadRssFeedTask().execute();
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		if (_feed == null)
		{
			return;
		}
		
		// Open the selected RSS item in the browser
		SyndEntry entry = (SyndEntry) _feed.getEntries().get(position);
		String link = entry.getLink(); 
		Log.i(TAG, link);
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(link));
		this.startActivity(intent);
	}
	
	
	//***************************************
    // Private methods
    //*************************************** 
	private void refreshRssFeed(SyndFeed feed) 
	{	
		_feed = feed;
		
		if (feed == null) 
		{
			return;
		}
		
		setTitle(feed.getTitle());
		
		SyndFeedListAdapter adapter = new SyndFeedListAdapter(this, _feed);
		setListAdapter(adapter);
	}
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadRssFeedTask extends AsyncTask<Void, Void, SyndFeed> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}
		
		@Override
		protected SyndFeed doInBackground(Void... params) 
		{
			try 
			{				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Configure the SyndFeed message converter.
				SyndFeedHttpMessageConverter converter = new SyndFeedHttpMessageConverter();
				List<MediaType> mediaTypes = new ArrayList<MediaType>();
				mediaTypes.add(MediaType.TEXT_XML);
				converter.setSupportedMediaTypes(mediaTypes);
				
				// Add the SyndFeed message converter to the RestTemplate instance
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				messageConverters.add(converter);
				restTemplate.setMessageConverters(messageConverters);
								
				// The URL for making the request
				final String url = "http://blog.springsource.com/feed";
				
				// Initiate the request and return the results
				return restTemplate.getForObject(url, SyndFeed.class);
			} 
			catch(Exception e) 
			{
				logException(e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(SyndFeed feed) 
		{
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			refreshRssFeed(feed);
		}
	}
}
