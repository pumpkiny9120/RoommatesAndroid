/**
 * Display login form and a sign up button.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates;

import java.io.IOException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oose2013.group7.roommates.common.commands.SignInCommand;
import com.oose2013.group7.roommates.common.enums.MessageUtils;
import com.oose2013.group7.roommates.common.interfaces.EventListener;
import com.oose2013.group7.roommates.common.interfaces.UserEvent;
import com.oose2013.group7.roommates.services.NetworkServices;
import com.oose2013.group7.roommates.services.NetworkServices.Connect;


public class Login extends Activity implements EventListener<UserEvent>{
	//private webService mTcpClient;
	
	private Button signinButton;
	private Button signupButton;
	private EditText usernameView;
	private EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login);
		
		//new connectTask().execute("");

		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		usernameView = (EditText) findViewById(R.id.login_username_input);
		passwordView = (EditText) findViewById(R.id.login_password_input);
		
		// get saved username
		SharedPreferences pref = getApplicationContext().getSharedPreferences("Roommates", 0);
		String username = pref.getString("username", null);
		usernameView.setText(username);
		
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
    	String username = usernameView.getText().toString();
    	String password = passwordView.getText().toString();

    	if(username.length() == 0 || password.length() == 0) {
			Toast.makeText(this, "Please fill up all fields.", Toast.LENGTH_LONG).show();
			return;
		}
    	
    	// save username locally
    	SharedPreferences pref = getApplicationContext().getSharedPreferences("Roommates", 0);
    	Editor editor = pref.edit();
    	editor.putString("username", username);
    	editor.commit();
    	
    	NetworkServices networkServices = NetworkServices.getNetworkServices();
    	if (!networkServices.isConnected()) {
    		networkServices.connect();
    	}
    	else {
    		SignInCommand cmd = new SignInCommand(username, password);
    		networkServices.sendMessage(cmd);
    	}
    }

	@Override
	public void eventReceived(UserEvent event) throws IOException {
		if ((event.getMessage()).equals(MessageUtils.SIGNIN_SUCCESS)) {
	    	Intent i = new Intent(Login.this, Lobby.class);
	        startActivity(i);

	        // close this activity
	        finish();
		}
		else if ((event.getMessage()).equals(MessageUtils.SIGNIN_FAIL)) {
			Toast toast = Toast.makeText(this, "Failed to login.", Toast.LENGTH_SHORT);
        	toast.show();
		}
	}

}
