package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

public abstract class AbstractAsyncActivity extends Activity implements AsyncActivity
{
	protected String TAG = "AsyncListActivity";
	private ProgressDialog _progressDialog;
	
	public void showLoadingProgressDialog() 
	{
		_progressDialog = ProgressDialog.show(this, "",  "Loading. Please wait...", true);
	}
		
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null) {
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
