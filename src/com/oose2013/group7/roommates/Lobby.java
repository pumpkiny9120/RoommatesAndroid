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
	private ImageView datingRoomButton;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lobby);
 
        gamingRoomButton = (ImageView) findViewById(R.id.gamingroom_text); 
        gamingRoomButton.setOnClickListener(this);
        datingRoomButton = (ImageView) findViewById(R.id.datingroom_text); 
        datingRoomButton.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
    	if (v.equals(gamingRoomButton)) {
    		Log.i("Describe", "joining game");
        	Intent i = new Intent(Lobby.this, DescribeLoading.class);
            startActivity(i);
    	}
    	else if (v.equals(datingRoomButton)) {
    		Log.i("FindMe", "joining game");
        	Intent i = new Intent(Lobby.this, FindMeMain.class);
            startActivity(i);
    	}
    }
}
