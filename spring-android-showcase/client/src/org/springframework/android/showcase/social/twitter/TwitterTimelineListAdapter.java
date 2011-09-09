/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.android.showcase.social.twitter;

import java.util.List;

import org.springframework.android.showcase.R;
import org.springframework.social.twitter.api.Tweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class TwitterTimelineListAdapter extends BaseAdapter {

    private List<Tweet> tweets;
    private final LayoutInflater layoutInflater;

    public TwitterTimelineListAdapter(Context context, List<Tweet> tweets) {
        this.tweets = tweets;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return tweets == null ? 0 : tweets.size();
    }

    public Tweet getItem(int position) {
        return tweets.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.twitter_timeline_list_item, parent, false);
        }

        TextView t = (TextView) view.findViewById(R.id.tweet_from_user);
        t.setText(tweet.getFromUser());

        t = (TextView) view.findViewById(R.id.tweet_created_at);
        String date = tweet.getCreatedAt() == null ? "" : tweet.getCreatedAt().toString();
        t.setText(date);

        t = (TextView) view.findViewById(R.id.tweet_text);
        t.setText(tweet.getText());

        return view;
    }

}
