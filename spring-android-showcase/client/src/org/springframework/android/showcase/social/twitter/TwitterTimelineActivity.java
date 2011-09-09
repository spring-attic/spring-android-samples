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

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class TwitterTimelineActivity extends AbstractAsyncListActivity {

    protected static final String TAG = TwitterProfileActivity.class.getSimpleName();

    private Twitter twitter;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitter = getApplicationContext().getConnectionRepository().findPrimaryConnection(Twitter.class).getApi();
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchTimelineTask().execute();
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult(List<Tweet> tweets) {
        TwitterTimelineListAdapter adapter = new TwitterTimelineListAdapter(this, tweets);
        setListAdapter(adapter);
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class FetchTimelineTask extends AsyncTask<Void, Void, List<Tweet>> {

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showProgressDialog("Fetching timeline...");
        }

        @Override
        protected List<Tweet> doInBackground(Void... params) {
            try {
                return twitter.timelineOperations().getHomeTimeline();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Tweet> tweets) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            showResult(tweets);
        }

    }

}
