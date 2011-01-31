package org.springframework.android.showcase;


public interface AsyncActivity 
{
	public void showLoadingProgressDialog(); 
		
	public void dismissProgressDialog();
	
	public void logException(Exception e); 
}
