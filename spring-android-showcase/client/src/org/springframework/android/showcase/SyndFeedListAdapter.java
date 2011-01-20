package org.springframework.android.showcase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

public class SyndFeedListAdapter extends BaseAdapter 
{
	private SyndFeed _syndFeed;
	private final LayoutInflater _layoutInflater;

	public SyndFeedListAdapter(Context context, SyndFeed feed) 
	{
		_syndFeed = feed;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return _syndFeed.getEntries().size();
	}

	public Object getItem(int position) 
	{
		return _syndFeed.getEntries().get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		SyndEntry syndEntry = (SyndEntry) getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(R.layout.synd_feed_list_item, parent, false);
		}

		TextView t = (TextView) view.findViewById(R.id.synd_feed_title);
		t.setText(syndEntry.getTitle());
		
		t = (TextView) view.findViewById(R.id.synd_feed_date);
		t.setText(syndEntry.getPublishedDate().toString());
		
		t = (TextView) view.findViewById(R.id.synd_feed_description);
		t.setText(syndEntry.getDescription().getValue());

		return view;
	}
}
