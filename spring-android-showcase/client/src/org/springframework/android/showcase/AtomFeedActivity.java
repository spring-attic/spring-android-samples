package org.springframework.android.showcase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
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

public class AtomFeedActivity extends AbstractAsyncListActivity 
{
	protected String TAG = "AtomFeedActivity";
	
	private Feed _feed;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setTitle(null);
		
		// initiate the asynchronous network request
		new DownloadAtomFeedTask().execute();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		Entry entry = (Entry) _feed.getEntries().get(position);
		List alternateLinks = entry.getAlternateLinks();
		if (alternateLinks.size() > 0)
		{
			Link link = (Link) alternateLinks.get(0);
			Log.i(TAG, link.getHref());
			Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(link.getHref()));
			this.startActivity(intent);
		}
	}
	
	
	//***************************************
    // Private methods
    //*************************************** 
	private void refreshAtomFeed(Feed feed) 
	{	
		_feed = feed;
		
		if (feed == null) 
		{
			return;
		}
		
		setTitle(feed.getTitle());
		
		AtomFeedListAdapter adapter = new AtomFeedListAdapter(this, feed);		
		setListAdapter(adapter);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadAtomFeedTask extends AsyncTask<Void, Void, Feed> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}
		
		@Override
		protected Feed doInBackground(Void... params) 
		{
			try 
			{				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Configure the ATOM message converter
				AtomFeedHttpMessageConverter converter = new AtomFeedHttpMessageConverter();
				List<MediaType> mediaTypes = new ArrayList<MediaType>();
				mediaTypes.add(new MediaType("application", "xml"));
				converter.setSupportedMediaTypes(mediaTypes);
				
				// Add the ATOM message converter to the RestTemplate instance
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				messageConverters.add(converter);
				restTemplate.setMessageConverters(messageConverters);
								
				// The URL for making the request
				final String url = "https://jira.springframework.org/plugins/servlet/streams";
				
				// Initiate the request and return the results
				return restTemplate.getForObject(url, Feed.class);
			} 
			catch(Exception e) 
			{
				logException(e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Feed feed) 
		{
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			refreshAtomFeed(feed);
		}
	}
}
