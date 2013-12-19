package com.oose2013.group7.roommates.games.describe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.oose2013.group7.roommates.GameModel;

/**
 * The Describe Game model.
 */
public class DescribeModel extends GameModel implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The state. */
	private String state;
	
	/** The round. */
	private Integer round;
	
	/** The word given. */
	private String wordGiven;
	
	/** The describing words. */
	private ArrayList<String> describingWords;
	
	/** The usernames. */
	private ArrayList<String> usernames;
	// listeners
	
	/**
	 * Instantiates a new describe model.
	 */
	public DescribeModel() {
		// initialization
		state = "describing";
		round = 1;
		wordGiven = "Apple";
		describingWords = new ArrayList<String>(Arrays.asList("Round","Sweet","Crunchy","Red"));
		usernames = new ArrayList<String>(Arrays.asList("Myself","User1","User2","User3"));
	}
	
	/**
	 * Update description.
	 *
	 * @param userIndex the user index
	 * @param word the word
	 */
	public void updateDescription(int userIndex, String word) {
		// TODO implement
		// update word
		
		// fire listeners
	}
	
	// TODO implement on server side and client side, but differently
//	public void fireListeners() {
//		
//	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Gets the round.
	 *
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}
	
	/**
	 * Gets the word given.
	 *
	 * @return the word given
	 */
	public String getWordGiven() {
		return wordGiven;
	}
	
	/**
	 * Gets the describing words.
	 *
	 * @return the describing words
	 */
	public ArrayList<String> getDescribingWords() {
		return describingWords;
	}
	
	/**
	 * Gets the usernames.
	 *
	 * @return the usernames
	 */
	public ArrayList<String> getUsernames() {
		return usernames;
	}
}
