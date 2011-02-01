package org.springframework.android.showcase;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity
{   
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		String[] options = getResources().getStringArray(R.array.main_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		setListAdapter(arrayAdapter);
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		
		switch(position) 
		{
	      	case 0:
			    intent.setClass(this, HttpGetActivity.class);
			    startActivity(intent);
	      		break;
	      	case 1:
	      		intent.setClass(this, HttpGetParametersActivity.class);
			    startActivity(intent);
	      		break;
	      	case 2:
			    intent.setClass(this, HttpPostActivity.class);
			    startActivity(intent);
	      		break;
	      	case 3:
	      		intent.setClass(this, RssActivity.class);
	      		startActivity(intent);
	      		break;
	      	case 4:
	      		intent.setClass(this, AtomActivity.class);
	      		startActivity(intent);
	      		break;
	      	default:
	      		break;
		}
	}
}
