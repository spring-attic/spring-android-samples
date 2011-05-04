package org.springframework.android.showcase.social.facebook;

import org.springframework.android.showcase.AbstractAsyncActivity;
import org.springframework.android.showcase.R;
import org.springframework.social.facebook.api.FacebookApi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FacebookWallPostActivity extends AbstractAsyncActivity 
{
	protected static final String TAG = FacebookWallPostActivity.class.getSimpleName();

	private FacebookApi _facebookApi;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.facebook_wall_post_activity_layout);
		
		_facebookApi = getApplicationContext().getConnectionRepository().findPrimaryConnectionToApi(FacebookApi.class).getApi();
		
		// Initiate the POST request when the button is clicked
		final Button button = (Button) findViewById(R.id.button_submit);
		button.setOnClickListener(new View.OnClickListener() 
			{
            	public void onClick(View v) 
            	{
            		// hide the soft keypad
            		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            		EditText editText = (EditText) findViewById(R.id.edit_text_wall_post);
            		inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            		
            		// start asynchronous facebook wall post 
            		new PostTweetTask().execute();
            	}
			}
		);
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showResult(String result)
	{
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class PostTweetTask extends AsyncTask<Void, Void, String> 
	{	
		private String _wallPostText;
		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Posting to Wall...");
			
			// retrieve the text from the EditText field
			EditText editText = (EditText) findViewById(R.id.edit_text_wall_post);
			_wallPostText = editText.getText().toString();
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{
			try
			{
				_facebookApi.feedOperations().updateStatus(_wallPostText);
				return "Status updated";
			}
			catch(Exception e)
			{
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "An error occurred. See the log for details";
			}
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			showResult(result);
		}
	}
}
