package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.util.Log;

public class AsyncListActivity extends ListActivity
{	
	protected String TAG = "AsyncListActivity";
	private ProgressDialog _progressDialog;
	
	protected void showLoadingProgressDialog() 
	{
		_progressDialog = ProgressDialog.show(this, "",  "Loading. Please wait...", true);
	}
		
	protected void dismissProgressDialog() 
	{
		if (_progressDialog != null) {
			_progressDialog.dismiss();
		}
	}
	
	protected void logException(Exception e) 
	{
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}
}
