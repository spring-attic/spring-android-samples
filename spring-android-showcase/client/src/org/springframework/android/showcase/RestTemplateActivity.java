package org.springframework.android.showcase;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class RestTemplateActivity extends ListActivity 
{
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
//		String[] options = getResources().getStringArray(R.array.rest_template_options);
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
//		setListAdapter(arrayAdapter);
		
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	    alertDialog.setTitle("To run examples...");
	    alertDialog.setMessage("The Spring Android Showcase server must be running in tc Server");
	    alertDialog.setButton("OK", 
	    		new DialogInterface.OnClickListener() 
	    		{
	    			public void onClick(DialogInterface dialog, int which) {
    					return;
	    			} 
    			});
	    
	    alertDialog.show();
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
			    intent.setClass(this, HttpGetJsonActivity.class);
			    startActivity(intent);
	      		break;
	      	case 1:
			    intent.setClass(this, HttpPostStringActivity.class);
			    startActivity(intent);
	      		break;
	      	default:
	      		break;
		}
	}
}
