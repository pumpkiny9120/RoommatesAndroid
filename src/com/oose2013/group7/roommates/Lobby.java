package com.oose2013.group7.roommates;


import com.oose2013.group7.roommates.games.describe.DescribeLoading;
import com.oose2013.group7.roommates.games.findme.FindMeMain;
import com.oose2013.group7.roommates.profile.Profile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Giving the choices of going to Waiting Room, Gaming Room,
 * Dating Room or profile page.
 */
public class Lobby extends Activity implements OnClickListener{
	
	/** The gaming room button. */
	private ImageView gamingRoomButton;
	
	/** The dating room button. */
	private ImageView datingRoomButton;
	
	/** The profile button. */
	private ImageView profileButton;
 
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lobby);
 
        gamingRoomButton = (ImageView) findViewById(R.id.gamingroom_text); 
        gamingRoomButton.setOnClickListener(this);
        datingRoomButton = (ImageView) findViewById(R.id.datingroom_text); 
        datingRoomButton.setOnClickListener(this);
        profileButton = (ImageView) findViewById(R.id.profile_text); 
        profileButton.setOnClickListener(this);
    }
    
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
	public void onClick(View v) {
    	if (v.equals(gamingRoomButton)) {
    		Log.d("Describe", "Joining game");
        	Intent i = new Intent(Lobby.this, DescribeLoading.class);
            startActivity(i);
    	}
    	else if (v.equals(datingRoomButton)) {
    		Log.d("FindMe", "Joining game");
        	Intent i = new Intent(Lobby.this, FindMeMain.class);
            startActivity(i);
    	}
    	else if (v.equals(profileButton)) {
    		Log.d("Profile", "Joining");
        	Intent i = new Intent(Lobby.this, Profile.class);
            startActivity(i);
    	}
    }
}
