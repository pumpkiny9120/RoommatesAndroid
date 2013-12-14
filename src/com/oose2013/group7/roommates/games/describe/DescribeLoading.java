/**
 * Loading screen for the game Describe. Displays instructions and waits
 * for connecting the server.
 * 
 * @author      Yanan Wang
 */
package com.oose2013.group7.roommates.games.describe;

import com.oose2013.group7.roommates.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DescribeLoading extends Activity implements OnClickListener{

	//TODO connect the server and join the game
	private Button joinButton;
    private DescribeModel model;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_describeloading);
 
        joinButton = (Button) findViewById(R.id.describe_join_button); 
		
		joinButton.setOnClickListener(this);
		joinButton.setEnabled(false);
		
		new getModelTask().execute();
		
    }
    
    @Override
	public void onClick(View v) {
    	Log.i("Describe", "joining game");
    	Intent i = new Intent(DescribeLoading.this, DescribeMain.class);
    	i.putExtra("model", model);
        startActivity(i);
//      // close this activity
//      finish();
    }
    
    private class getModelTask extends AsyncTask<Void, Void, DescribeModel> {
        
        @Override
		protected void onPostExecute(DescribeModel result) {
        	model = result;
        	joinButton.setEnabled(true);
        	Log.i("Describe", "getModel finished");
        }

		@Override
		protected DescribeModel doInBackground(Void... params) {
			return new DescribeModel();
		}
    }

}
