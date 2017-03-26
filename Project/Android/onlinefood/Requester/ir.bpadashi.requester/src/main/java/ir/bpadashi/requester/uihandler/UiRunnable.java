package ir.bpadashi.requester.uihandler;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public class UiRunnable implements Runnable,IUiRunnable {
	
	private Context context;
	private Fragment fragment;
	private boolean isFragment;
	
	
	@Override
	public void run() {
		
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Context context,Object... param) {

		
	}
	
	@Override
	public void onSuccess(Fragment context,Object... param) {

		
	}

	@Override
	public void onError(Exception exception, String exceptionFarsi, String textToShow) {
		// TODO Auto-generated method stub
		
	}

	
	public UiRunnable(Context context) {
		this.context = context;
	}

	public UiRunnable(Fragment fragment) {
		this.fragment = fragment;
		this.context = fragment.getActivity();
		this.isFragment=true;
	}
	
	public void onShow(final Object... param){
		
		if(this.isFragment){
			onShowFragment(param);
			return;
		}else if (context == null)
			return;
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onSuccess(context,param);
			}
		});
		
	}
	
	public void onShowFragment(final Object... param) {
		if (fragment == null || fragment.getActivity()==null || fragment.getView()==null)
			return;
		((Activity) fragment.getActivity()).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onSuccess(fragment,param);
			}
		});
	}




}
