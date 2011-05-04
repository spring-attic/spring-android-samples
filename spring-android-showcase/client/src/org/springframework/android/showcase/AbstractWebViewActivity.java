package org.springframework.android.showcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public abstract class AbstractWebViewActivity extends Activity implements AsyncActivity 
{
	protected static final String TAG = AbstractWebViewActivity.class.getSimpleName();
	
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
		
		_webView = new WebView(this);
		setContentView(_webView);
		
		_webView.setWebChromeClient(
				new WebChromeClient() 
				{
		            public void onProgressChanged(WebView view, int progress)
		            {		            	
		            	showLoadingProgressDialog();
		            	_progressDialog.setProgress(progress);
		 
		                if (progress == 100)
		                {
		                	dismissProgressDialog();
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
		this.showProgressDialog("Loading...");
	}
	
	public void showProgressDialog(CharSequence message) 
	{
		if (_progressDialog == null)
		{
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			_progressDialog.setCancelable(false);
		}
		
		if (!_progressDialog.isShowing())
		{
			_progressDialog.setMessage(message);
			_progressDialog.show();
		}
	}
		
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null && !_destroyed) 
		{
			_progressDialog.dismiss();
		}
	}
}
