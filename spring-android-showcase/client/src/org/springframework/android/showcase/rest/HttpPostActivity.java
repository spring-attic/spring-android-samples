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
package org.springframework.android.showcase.rest;

import org.springframework.android.showcase.AbstractMenuActivity;
import org.springframework.android.showcase.R;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Roy Clarkson
 */
public class HttpPostActivity extends AbstractMenuActivity {

    // ***************************************
    // AbstractMenuActivity methods
    // ***************************************
    @Override
    protected String getDescription() {
        return getResources().getString(R.string.text_http_post_description);
    }

    @Override
    protected String[] getMenuItems() {
        return getResources().getStringArray(R.array.http_post_activity_options);
    }

    @Override
    protected OnItemClickListener getMenuOnItemClickListener() {
        return new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
                switch (position) {
                case 0:
                    startActivity(new Intent(parentView.getContext(), HttpPostStringActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(parentView.getContext(), HttpPostJsonXmlActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(parentView.getContext(), HttpPostMultiValueMapActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(parentView.getContext(), HttpPostFormDataActivity.class));
                    break;
                default:
                    break;
                }
            }
        };
    }

}
