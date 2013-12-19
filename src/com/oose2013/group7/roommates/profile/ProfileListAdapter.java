package com.oose2013.group7.roommates.profile;

import com.oose2013.group7.roommates.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Class ProfileListAdapter.
 */
public class ProfileListAdapter extends ArrayAdapter<FriendListItemHolder>{

    /** The context. */
    Context context; 
    
    /** The layout resource id. */
    int layoutResourceId;    
    
    /** The data. */
    FriendListItemHolder data[] = null;
    
    /**
     * Instantiates a new profile list adapter.
     *
     * @param context the context
     * @param layoutResourceId the layout resource id
     * @param data the data
     */
    public ProfileListAdapter(Context context, int layoutResourceId, FriendListItemHolder[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new FriendHolder();
            holder.profilePic = (ImageView)row.findViewById(R.id.friend_pic);
            holder.name = (TextView)row.findViewById(R.id.friend_name);
            
            row.setTag(holder);
        }
        else
        {
            holder = (FriendHolder)row.getTag();
        }
        
        FriendListItemHolder friend = data[position];
        holder.name.setText(friend.getName());
        holder.profilePic.setImageResource(friend.getProfilePic());
        
        return row;
    }
    
    /**
     * The Class FriendHolder.
     */
    public static class FriendHolder
    {
    	
	    /** The name. */
	    public TextView name;
    	
	    /** The status. */
	    public TextView status;
    	
	    /** The liking points. */
	    public TextView likingPoints;
    	
	    /** The profile pic. */
	    public ImageView profilePic;
    }
}