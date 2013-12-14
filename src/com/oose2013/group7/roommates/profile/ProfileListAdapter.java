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

public class ProfileListAdapter extends ArrayAdapter<FriendListItemHolder>{

    Context context; 
    int layoutResourceId;    
    FriendListItemHolder data[] = null;
    
    public ProfileListAdapter(Context context, int layoutResourceId, FriendListItemHolder[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

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
    
    public static class FriendHolder
    {
    	public TextView name;
    	public TextView status;
    	public TextView likingPoints;
    	public ImageView profilePic;
    }
}