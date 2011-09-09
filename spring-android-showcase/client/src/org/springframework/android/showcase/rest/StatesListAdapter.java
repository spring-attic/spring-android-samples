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

import java.util.List;

import org.springframework.android.showcase.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class StatesListAdapter extends BaseAdapter {

    private List<State> states;
    private final LayoutInflater layoutInflater;

    public StatesListAdapter(Context context, List<State> states) {
        this.states = states;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return states != null ? states.size() : 0;
    }

    public State getItem(int position) {
        return states.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.states_list_item, parent, false);
        }

        State state = getItem(position);
        if (state != null) {
            TextView t = (TextView) convertView.findViewById(R.id.state_name);
            t.setText(state.getFormattedName());
        }

        return convertView;
    }

}
