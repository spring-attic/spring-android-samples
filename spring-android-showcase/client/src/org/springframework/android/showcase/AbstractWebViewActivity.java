package org.springframework.android.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public abstract class AbstractWebViewActivity extends Activity 
{
	protected static final String TAG = AbstractWebViewActivity.class.getSimpleName();
	
	private WebView _webView;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_webView = new WebView(this);
		setContentView(_webView);
	}
	
	
	//***************************************
    // Protected methods
    //***************************************
	protected WebView getWebView()
	{
		return _webView;
	}
}
