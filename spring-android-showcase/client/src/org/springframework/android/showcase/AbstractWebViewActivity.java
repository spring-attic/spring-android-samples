package org.springframework.android.showcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public abstract class AbstractWebViewActivity extends Activity implements AsyncActivity 
{
	protected static final String TAG = AbstractWebViewActivity.class.getSimpleName();
	
	private Activity _activity;
	
	private WebView _webView;
	
	private ProgressDialog _progressDialog = null;
	
	private boolean _destroyed = false;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public MainApplication getApplicationContext()
	{
		return (MainApplication) super.getApplicationContext();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
		
		_webView = new WebView(this);
		setContentView(_webView);
		
		_activity = this;
		
		_webView.setWebChromeClient(
				new WebChromeClient() 
				{
		            public void onProgressChanged(WebView view, int progress)
		            {
		            	_activity.setTitle("Loading...");
		            	_activity.setProgress(progress * 100);
		            	
		            	if (progress == 100)
		            	{
		            		_activity.setTitle(R.string.app_name);
		            	}
		            }
				}
		);
	}
		
	
	//***************************************
    // Protected methods
    //***************************************
	protected WebView getWebView()
	{
		return _webView;
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public void showLoadingProgressDialog() 
	{
		showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(CharSequence message)
	{
		if (_progressDialog == null)
		{
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setIndeterminate(true);
		}
		
		_progressDialog.setMessage(message);
		_progressDialog.show();
	}
		
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null && !_destroyed) 
		{
			_progressDialog.dismiss();
		}
	}
}
