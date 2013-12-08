package com.oose2013.group7.roommates.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class NetworkServices extends Activity {

	private static final String APP_TAG = "Roommates";
	private static final String ACT_TAG = "NetworkServices: ";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
	}
	
    public static boolean isInternetOn(Context context) {
        Log.d(APP_TAG, ACT_TAG + "Checking internet connectivity...");
        ConnectivityManager con = null;
        con =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( con != null){
            Log.d(APP_TAG, ACT_TAG + "Internet available.");
            NetworkInfo result = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (result != null && result.isConnectedOrConnecting());
            return true;
        }
        Log.d(APP_TAG, ACT_TAG + "Internet NOT available.");
        return false;
    }
    
}    
