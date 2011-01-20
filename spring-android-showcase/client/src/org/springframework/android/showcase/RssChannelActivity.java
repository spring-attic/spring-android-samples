package org.springframework.android.showcase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Channel;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Item;

public class RssChannelActivity extends AsyncListActivity 
{
	protected String TAG = "RssChannelActivity";
	
	private Channel _channel;
	
	
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
		if (_channel == null)
		{
			return;
		}
		
		Item item = (Item) _channel.getItems().get(position);
		String uri = item.getUri();
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
		this.startActivity(intent);
	}
	
	
	//***************************************
    // Private methods
    //*************************************** 
	private void refreshRssFeed(Channel channel) 
	{	
		_channel = channel;
		
		if (channel == null) 
		{
			return;
		}
		
		setTitle(channel.getTitle());
		
		RssChannelListAdapter adapter = new RssChannelListAdapter(this, channel);
		setListAdapter(adapter);
	}
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadRssFeedTask extends AsyncTask<Void, Void, Channel> 
	{	
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}
		
		@Override
		protected Channel doInBackground(Void... params) 
		{
			try 
			{				
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();

				// Configure the RSS message converter.
				RssChannelHttpMessageConverter converter = new RssChannelHttpMessageConverter();
				List<MediaType> mediaTypes = new ArrayList<MediaType>();
				mediaTypes.add(new MediaType("text", "xml"));
				converter.setSupportedMediaTypes(mediaTypes);
				
				// Add the RSS message converter to the RestTemplate instance
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				messageConverters.add(converter);
				restTemplate.setMessageConverters(messageConverters);
				
				// The URL for making the request
				final String url = "http://blog.springsource.com/feed";
				
				// Initiate the request and return the results
				return restTemplate.getForObject(url, Channel.class);
			} 
			catch(Exception e) 
			{
				logException(e);
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Channel channel) 
		{
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			
			// return the list of states
			refreshRssFeed(channel);
		}
	}
}
