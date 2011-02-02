package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.util.Log;

public abstract class AbstractAsyncListActivity extends ListActivity implements AsyncActivity
{	
	protected String TAG = "AbstractAsyncListActivity";
	private ProgressDialog _progressDialog;
	
	public void showLoadingProgressDialog() 
	{
		_progressDialog = ProgressDialog.show(this, "",  "Loading. Please wait...", true);
	}
		
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null) 
		{
			_progressDialog.dismiss();
		}
	}
	
	public void logException(Exception e) 
	{
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}
}
