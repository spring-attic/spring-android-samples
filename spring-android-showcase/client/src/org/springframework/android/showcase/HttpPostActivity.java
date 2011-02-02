package org.springframework.android.showcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HttpPostActivity extends Activity 
{

	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.http_post_activity_layout);
		
		String[] options = getResources().getStringArray(R.array.http_post_activity_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		ListView listView = (ListView) this.findViewById(R.id.http_post_activity_options_list);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(
				new AdapterView.OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) 
					{
						Intent intent = new Intent();
						
						switch(position)
						{
							case 0:
								intent.setClass(parentView.getContext(), HttpPostStringActivity.class);
								startActivity(intent);
								break;
							case 1:
								intent.setClass(parentView.getContext(), HttpPostJsonXmlActivity.class);
								startActivity(intent);
								break;
							default:
								break;
						}
					}
				}
			);
	}
}
