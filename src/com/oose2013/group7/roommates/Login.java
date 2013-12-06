/**
 * Display login form and a sign up button.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.oose2013.group7.roommates.services.webService;
import android.os.AsyncTask;


public class Login extends Activity {
	private webService mTcpClient;
	
	private Button signinButton;
	private Button signupButton;
	private EditText username;
	private EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login);
		
		new connectTask().execute("");
		
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		username = (EditText) findViewById(R.id.login_username_input);
		password = (EditText) findViewById(R.id.login_password_input);
		
		signupButton = (Button) findViewById(R.id.login_signup_button); 
		signupButton.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
 
                // close this activity
                finish();
			}  
		}); 
		
		signinButton = (Button) findViewById(R.id.login_signin_button);  
		signinButton.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View v) {
				onSigninButtonClicked();
			}  
		});

	}

    private void onSigninButtonClicked() {
    	Log.i("Signin", username.getText().toString());
    	Log.i("Signin", password.getText().toString());
    	mTcpClient.sendMessage(username.getText().toString());
    	
    	Intent i = new Intent(Login.this, Lobby.class);
        startActivity(i);

        // close this activity
        finish();
    }

	public class connectTask extends AsyncTask<String,String, webService> {
		@Override
		protected webService doInBackground(String... message) {
			mTcpClient = new webService(new webService.OnMessageReceived() {
				@Override
				//here the messageReceived method is implemented
				public void messageReceived(String message) {
					//this method calls the onProgressUpdate
					publishProgress(message);
					}
				});
			mTcpClient.run();
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			// TODO: Add your codes here to deal with the update
		}
	}

}
