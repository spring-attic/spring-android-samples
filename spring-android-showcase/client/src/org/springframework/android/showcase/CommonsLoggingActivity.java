package org.springframework.android.showcase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CommonsLoggingActivity extends ListActivity 
{
	private Log _log;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		String[] options = getResources().getStringArray(R.array.commons_logging_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
		setListAdapter(arrayAdapter);
	}
	
	@Override
	public void onStart() 
	{
		super.onStart();
		_log = LogFactory.getLog(CommonsLoggingActivity.class);
		
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	    alertDialog.setTitle("To view logs...");
	    alertDialog.setMessage("run 'adb logcat' from the command line");
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
		
		switch(position) 
		{
	      	case 0:
	      		allLogs(_log);
	      		break;
	      	case 1:
	      		traceLog(_log);
	      		break;
	      	case 2:
	      		debugLog(_log);
	      		break;
	      	case 3:
	      		infoLog(_log);
	      		break;
	      	case 4:
	      		warnLog(_log);
	      		break;
	      	case 5:
	      		errorLog(_log);
	      		break;
	      	case 6:
	      		fatalLog(_log);
	      		break;
	      	default:
	      		break;
		}
	}
	
	//***************************************
    // Private methods
    //***************************************
	private void allLogs(Log log) 
	{
		traceLog(log);
		debugLog(log);
		infoLog(log);
		warnLog(log);
		errorLog(log);
		fatalLog(log);
	}
	
	private void traceLog(Log log)
	{
		String msg = "trace message";
	    Exception e = new Exception("testing trace log");
		android.util.Log.v(null, "isTraceEnabled: " + log.isTraceEnabled());
	    log.trace(msg);
	    log.trace(msg, e);
	}
	
	private void debugLog(Log log)
	{
		String msg = "debug message";
	    Exception e = new Exception("testing debug log");
		android.util.Log.v(null, "isDebugEnabled: " + log.isDebugEnabled());
	    log.debug(msg);
	    log.debug(msg, e);
	}
	
	private void infoLog(Log log)
	{
		String msg = "info message";
		Exception e = new Exception("testing info log");
		android.util.Log.v(null, "isInfoEnabled: " + log.isInfoEnabled());
	    log.info(msg);
	    log.info(msg, e);
	}
	
	private void warnLog(Log log)
	{
		String msg = "warn message";
	    Exception e = new Exception("testing warn log");
		android.util.Log.v(null, "isWarnEnabled: " + log.isWarnEnabled());
	    log.warn(msg);
	    log.warn(msg, e);
	}
	
	private void errorLog(Log log)
	{
		String msg = "error message";
	    Exception e = new Exception("testing error log");
		android.util.Log.v(null, "isErrorEnabled: " + log.isErrorEnabled());
	    log.error(msg);
	    log.error(msg, e);
	}
	
	private void fatalLog(Log log)
	{
		String msg = "fatal message";
	    Exception e = new Exception("testing fatal log");
		android.util.Log.v(null, "isFatalEnabled: " + log.isFatalEnabled());
	    log.fatal(msg);
	    log.fatal(msg, e);
	}
}
