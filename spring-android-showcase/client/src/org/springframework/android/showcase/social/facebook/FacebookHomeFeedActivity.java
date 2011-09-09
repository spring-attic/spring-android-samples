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
package org.springframework.android.showcase.social.facebook;

import java.util.List;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class FacebookHomeFeedActivity extends AbstractAsyncListActivity {

    protected static final String TAG = FacebookHomeFeedActivity.class.getSimpleName();

    private Facebook facebook;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.facebook = getApplicationContext().getConnectionRepository().findPrimaryConnection(Facebook.class).getApi();
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchWallFeedTask().execute();
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult(List<Post> entries) {
        FacebookFeedListAdapter adapter = new FacebookFeedListAdapter(this, entries);
        setListAdapter(adapter);
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class FetchWallFeedTask extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected void onPreExecute() {
            // before the network request begins, show a progress indicator
            showProgressDialog("Fetching home feed...");
        }

        @Override
        protected List<Post> doInBackground(Void... params) {
            try {
                return facebook.feedOperations().getHomeFeed();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Post> entries) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            showResult(entries);
        }

    }

}
