/**
 * SplashScreen is the loading screen when a user starts the app.
 * It demonstrates the logo and wait until the loading process finishes.
 * 
 * @author      Yanan Wang
 */

package com.oose2013.group7.roommates;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity{
	// TODO check network availability when waiting
    // splash screen timer, 5 seconds
    private static int SPLASH_TIME_OUT = 5000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
 
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
    }

}
