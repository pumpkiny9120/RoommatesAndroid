package com.oose2013.group7.roommates.profile;

public class FriendListItemHolder {
	private String name;
	private String status;
	private int likingPoints;
	private int profilePic;

    public FriendListItemHolder(String name, String status, int likingPoints, int profilePic) {
        this.name = name;
        this.status = status;
        this.likingPoints = likingPoints;
        this.profilePic = profilePic;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public int getLikingPoints() {
    	return likingPoints;
    }
    
    public int getProfilePic() {
    	return profilePic;
    }
}
