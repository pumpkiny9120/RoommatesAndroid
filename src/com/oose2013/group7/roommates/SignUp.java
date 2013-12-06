/**
 * Displays a sign up form and a log in button.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SignUp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_signup);
		
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Button signupButton = (Button) findViewById(R.id.signup_signup_button);  
		signupButton.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SignUp.this, SplashScreen.class);
                startActivity(i);
 
                // close this activity
                finish();
			}  
		}); 
		
		Button signinButton = (Button) findViewById(R.id.signup_signin_button);  
		signinButton.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
 
                // close this activity
                finish();
			}  
		});

	}
}
