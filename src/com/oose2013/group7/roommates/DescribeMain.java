/**
 * Describe game controller and view. It does dummy updates whenever
 * the model changes.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates;

import com.oose2013.group7.roommates.common.interfaces.DescribeGameProxy;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DescribeMain extends Activity implements GameListener, DescribeGameProxy{
	
	private String user;
	private DescribeModel model;
	private TextView round;
	//private TextView state;
	private TextView display;
	private TextView word0;
	private TextView word1;
	private TextView word2;
	private TextView word3;
	private TextView username0;
	private TextView username1;
	private TextView username2;
	private TextView username3;
	private TextView input;
	private TextView countdown;
	private Button submit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_describe);
		
        model = (DescribeModel) getIntent().getSerializableExtra("model");
        // TODO user = get username from bundle
        user = "currentUser";
        
        round = (TextView) findViewById(R.id.describe_round);
        display = (TextView) findViewById(R.id.describe_display);
        word0 = (TextView) findViewById(R.id.describe_name);
        word1 = (TextView) findViewById(R.id.describe_word1);
        word2 = (TextView) findViewById(R.id.describe_word2);
        word3 = (TextView) findViewById(R.id.describe_word3);
        username0 = (TextView) findViewById(R.id.describe_username0);
        username1 = (TextView) findViewById(R.id.describe_username1);
        username2 = (TextView) findViewById(R.id.describe_username2);
        username3 = (TextView) findViewById(R.id.describe_username3);
        input = (TextView) findViewById(R.id.describe_input);
        countdown = (TextView) findViewById(R.id.describe_countdown);
        submit = (Button) findViewById(R.id.describe_submit);
        
        submit.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View v) {
				String description = input.getText().toString();
				setDescription(description, user);
			}  
		});
      
        initView();
        updateView();
    }
    
	@Override
	public void setDescription(String text, String userName) {
		
		// TODO send a command object to the server to update an description
	}
    
    public void initView() {
        // TODO initialize player names, etc
    	username0.setText(model.getUsernames().get(0));
    	username1.setText(model.getUsernames().get(1));
    	username2.setText(model.getUsernames().get(2));
    	username3.setText(model.getUsernames().get(3));
    }
    
    public void updateView() {
        
        round.setText("Round " + (model.getRound()).toString());
        
        if (model.getState().equals("describing")) {
        	// players are typing words
        	display.setText(model.getWordGiven());
        	// show input box instead of result
            word0.setVisibility(View.INVISIBLE);
            input.setVisibility(View.VISIBLE);
            countdown.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            word0.setText("describing...");
            word1.setText("describing...");
            word2.setText("describing...");
            word3.setText("describing...");
        }
        else if (model.getState().equals("described")) {
        	// finished describing, show result
        	display.setText("showing result...");
        	word0.setVisibility(View.VISIBLE);
        	input.setVisibility(View.INVISIBLE);
        	countdown.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);
        	word0.setText(model.getDescribingWords().get(0));
            word1.setText(model.getDescribingWords().get(1));
            word2.setText(model.getDescribingWords().get(2));
            word3.setText(model.getDescribingWords().get(3));
        }
        else {
        	// show relations
        	display.setText("matching result...");
        	word0.setVisibility(View.VISIBLE);
        	input.setVisibility(View.INVISIBLE);
        	countdown.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);
        	word0.setText(model.getDescribingWords().get(0));
            word1.setText(model.getDescribingWords().get(1));
            word2.setText(model.getDescribingWords().get(2));
            word3.setText(model.getDescribingWords().get(3));
        }
    }

	@Override
	public void modelChanged(GameEvent event) {
		updateView();
	}
}
