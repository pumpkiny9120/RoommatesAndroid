/**
 * SplashScreen is the loading screen when a user starts the app.
 * It demonstrates the logo and wait until the loading process finishes.
 * It checks the internet connectivity.
 * 
 * @author      Yanan Wang
 */

package com.oose2013.group7.roommates;

import com.oose2013.group7.roommates.services.NetworkServices;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


/**
 * Checks the internet availability and connect to the server.
 */
public class SplashScreen extends Activity{
	
	/** The Constant APP_TAG. */
	private static final String APP_TAG = "Roommates";
	
	/** The Constant ACT_TAG. */
	private static final String ACT_TAG = "SplashScreen: ";
    // splash screen timer, 5 seconds
    /** The splash time out. */
    private static int SPLASH_TIME_OUT = 5000;
 
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        Log.d(APP_TAG, ACT_TAG + "Roommates has launched.");
        
        new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
                // jump to login screen when timer fires
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        if (!NetworkServices.isInternetOn(this)) {
        	Toast toast = Toast.makeText(this, "Network not available.", Toast.LENGTH_SHORT);
        	toast.show();
        }
        else {
        	Toast toast = Toast.makeText(this, "Connected to Internet.", Toast.LENGTH_SHORT);
        	toast.show();
        }
        
        NetworkServices networkServices = NetworkServices.getNetworkServices();
        networkServices.connect();
//    	if (!networkServices.isConnected()) {
//    		networkServices.connect();
//    		Toast toast = Toast.makeText(this, "Service not available.", Toast.LENGTH_SHORT);
//        	toast.show();
//    	}
//    	else {
//    		Toast toast = Toast.makeText(this, "Service available.", Toast.LENGTH_SHORT);
//        	toast.show();
//    	}
    }

}
