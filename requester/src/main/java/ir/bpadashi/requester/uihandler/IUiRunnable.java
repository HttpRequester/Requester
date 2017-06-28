package ir.bpadashi.requester.uihandler;

import android.content.Context;
import android.support.v4.app.Fragment;

public interface IUiRunnable  {
	
	public void onStart();
	
	
	void onError(Exception exception,String exceptionFarsi,String textToShow);
	void onSuccess(Fragment context, Object... param);
	void onSuccess(Context context, Object... param);
	
    
}
