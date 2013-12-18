/**
 * Displays a sign up form and a log in button.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates;

import java.io.IOException;

import com.oose2013.group7.roommates.common.commands.SignInCommand;
import com.oose2013.group7.roommates.common.commands.SignUpCommand;
import com.oose2013.group7.roommates.common.enums.MessageUtils;
import com.oose2013.group7.roommates.common.interfaces.EventListener;
import com.oose2013.group7.roommates.common.interfaces.UserEvent;
import com.oose2013.group7.roommates.services.NetworkServices;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends Activity implements EventListener<UserEvent>{
	
	private EditText usernameView;
	private EditText passwordView;
	private EditText retypePasswordView;
	private EditText emailView;
	private RadioGroup genderView;
	
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
				signUp();
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
	
	private void signUp() {
		usernameView = (EditText) findViewById(R.id.signup_username_input);
		passwordView = (EditText) findViewById(R.id.signup_password_input);
		retypePasswordView = (EditText) findViewById(R.id.signup_retype_password_input);
		emailView = (EditText) findViewById(R.id.signup_email_input);
		genderView = (RadioGroup) findViewById(R.id.signup_gender_input);
		
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();
		String retypePassword = retypePasswordView.getText().toString();
		String email = emailView.getText().toString();
		String gender;
		int selectedId = genderView.getCheckedRadioButtonId();
		Button genderButton = (RadioButton) findViewById(selectedId);
		if(genderButton == null) {
			Toast.makeText(this, "Please fill up all fields.", Toast.LENGTH_LONG).show();
			return;
		}
		gender = genderButton.getText().toString();

		if(username.length() == 0 || password.length() == 0 || 
				retypePassword.length() == 0 || email.length() == 0) {
			Toast.makeText(this, "Please fill up all fields.", Toast.LENGTH_LONG).show();
			return;
		}
		else if (!retypePassword.equals(password)) {
			Toast.makeText(this, "Retyped password doesn't match.", Toast.LENGTH_LONG).show();
			return;
		}
		
    	NetworkServices networkServices = NetworkServices.getNetworkServices();
    	if (!networkServices.isConnected()) {
    		networkServices.connect();
    	}
    	else {
    		SignUpCommand cmd = new SignUpCommand(username, password, email, gender);
    		networkServices.sendMessage(cmd);
    	}
	}

	@Override
	public void eventReceived(UserEvent event) throws IOException {
		if ((event.getMessage()).equals(MessageUtils.SIGNIN_SUCCESS)) {
	    	Intent i = new Intent(SignUp.this, Lobby.class);
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
