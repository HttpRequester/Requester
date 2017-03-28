package ir.bpadashi.requester.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	final private int TYPE_WIFI = 1;
	final private int TYPE_MOBILE = 2;
	final private int TYPE_NOT_CONNECTED = 0;

	private Context context;

	public NetworkUtil(Context context) {
		this.context = context;
	}

	private int getConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public boolean getConnectivityStatus() {

		int conn = getConnectivity();

		boolean status = false;

		if (conn == TYPE_WIFI)
			status = true;

		if (conn == TYPE_MOBILE)
			status = true;

		return status;
	}
}