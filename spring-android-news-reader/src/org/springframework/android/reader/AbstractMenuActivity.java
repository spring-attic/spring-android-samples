/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.android.reader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public abstract class AbstractMenuActivity extends Activity {

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity_layout);

		final TextView textViewDescription = (TextView) this.findViewById(R.id.text_view_description);
		textViewDescription.setText(getDescription());

		final ListView listViewMenu = (ListView) this.findViewById(R.id.list_view_menu_items);
		listViewMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getMenuItems()));
		listViewMenu.setOnItemClickListener(getMenuOnItemClickListener());
	}

	// ***************************************
	// Abstract methods
	// ***************************************
	protected abstract String getDescription();

	protected abstract String[] getMenuItems();

	protected abstract OnItemClickListener getMenuOnItemClickListener();

}
