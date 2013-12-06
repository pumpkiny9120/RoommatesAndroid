package com.oose2013.group7.roommates;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Lobby extends Activity implements OnClickListener{
	private ImageView gamingRoomButton;
    private DescribeModel model;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lobby);
 
        gamingRoomButton = (ImageView) findViewById(R.id.gamingroom_text); 
		
        gamingRoomButton.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
    	Log.i("Describe", "joining game");
    	Intent i = new Intent(Lobby.this, DescribeLoading.class);
    	i.putExtra("model", model);
        startActivity(i);
//      // close this activity
//      finish();
    }
}
