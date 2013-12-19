package com.oose2013.group7.roommates.profile;

import com.oose2013.group7.roommates.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Profile page with a friend list.
 */
public class Profile extends Activity {

    /** The friendlist. */
    private ListView friendlist;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        FriendListItemHolder friend_data[] = new FriendListItemHolder[]
        {
            new FriendListItemHolder("Rujuta", "So tired today!", 25, R.drawable.icon1),
            new FriendListItemHolder("Bowen", "Still working in the lab this late...", 13, R.drawable.icon2)
        };
        
        ProfileListAdapter adapter = new ProfileListAdapter(this, R.layout.layout_friendlist_row, friend_data);
        
        
        friendlist = (ListView)findViewById(R.id.friendlist);
         
        View header = (View)getLayoutInflater().inflate(R.layout.layout_profile_row, null);
        friendlist.addHeaderView(header);
        
        friendlist.setAdapter(adapter);
    }

}