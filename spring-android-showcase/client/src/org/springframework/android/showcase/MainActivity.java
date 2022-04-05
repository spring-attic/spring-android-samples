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

package org.springframework.android.showcase;

import org.springframework.android.showcase.rest.HttpGetActivity;
import org.springframework.android.showcase.rest.HttpPostActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class MainActivity extends AbstractMenuActivity {

	// ***************************************
	// AbstractMenuActivity methods
	// ***************************************
	@Override
	protected String getDescription() {
		return getResources().getString(R.string.main_menu);
	}

	@Override
	protected String[] getMenuItems() {
		return getResources().getStringArray(R.array.main_menu_items);
	}

	@Override
	protected OnItemClickListener getMenuOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Class<?> cls = null;
				switch (position) {
				case 0:
					cls = HttpGetActivity.class;
					break;
				case 1:
					cls = HttpPostActivity.class;
					break;
				default:
					break;
				}
				startActivity(new Intent(parentView.getContext(), cls));
			}
		};
	}

}
