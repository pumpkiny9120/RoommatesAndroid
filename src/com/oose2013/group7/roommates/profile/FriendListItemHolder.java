package com.oose2013.group7.roommates.profile;

/**
 * Holds the items in a row of a friend list.
 */
public class FriendListItemHolder {
	
	/** The name. */
	private String name;
	
	/** The status. */
	private String status;
	
	/** The liking points. */
	private int likingPoints;
	
	/** The profile pic. */
	private int profilePic;

    /**
     * Instantiates a new friend list item holder.
     *
     * @param name the name
     * @param status the status
     * @param likingPoints the liking points
     * @param profilePic the profile pic
     */
    public FriendListItemHolder(String name, String status, int likingPoints, int profilePic) {
        this.name = name;
        this.status = status;
        this.likingPoints = likingPoints;
        this.profilePic = profilePic;
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
    	return status;
    }
    
    /**
     * Gets the liking points.
     *
     * @return the liking points
     */
    public int getLikingPoints() {
    	return likingPoints;
    }
    
    /**
     * Gets the profile pic.
     *
     * @return the profile pic
     */
    public int getProfilePic() {
    	return profilePic;
    }
}
