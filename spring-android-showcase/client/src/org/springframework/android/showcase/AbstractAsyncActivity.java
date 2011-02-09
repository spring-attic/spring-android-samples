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
package org.springframework.android.showcase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public abstract class AbstractAsyncActivity extends Activity implements AsyncActivity
{
	protected static final String TAG = "AbstractAsyncActivity";
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
