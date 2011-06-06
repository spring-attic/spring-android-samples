package org.springframework.android.showcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public abstract class AbstractWebViewActivity extends Activity implements AsyncActivity {
	
	protected static final String TAG = AbstractWebViewActivity.class.getSimpleName();
	
	private Activity activity;
	
	private WebView webView;
	
	private ProgressDialog progressDialog = null;
	
	private boolean _destroyed = false;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public MainApplication getApplicationContext() {
		return (MainApplication) super.getApplicationContext();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
		webView = new WebView(this);
		setContentView(webView);
		activity = this;
		
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				activity.setTitle("Loading...");
				activity.setProgress(progress * 100);
				if (progress == 100) {
					activity.setTitle(R.string.app_name);
				}
			}
		});
	}
		
	
	//***************************************
    // Protected methods
    //***************************************
	protected WebView getWebView() {
		return webView;
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public void showLoadingProgressDialog() {
		showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}
		
		progressDialog.setMessage(message);
		progressDialog.show();
	}
		
	public void dismissProgressDialog() {
		if (progressDialog != null && !_destroyed) {
			progressDialog.dismiss();
		}
	}
	
}
