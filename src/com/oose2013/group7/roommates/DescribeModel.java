package com.oose2013.group7.roommates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DescribeModel extends GameModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private Integer round;
	private String wordGiven;
	private ArrayList<String> describingWords;
	private ArrayList<String> usernames;
	// listeners
	
	public DescribeModel() {
		// initialization
		state = "describing";
		round = 1;
		wordGiven = "Apple";
		describingWords = new ArrayList<String>(Arrays.asList("Round","Sweet","Crunchy","Red"));
		usernames = new ArrayList<String>(Arrays.asList("Myself","User1","User2","User3"));
	}
	
	public void updateDescription(int userIndex, String word) {
		// TODO implement
		// update word
		
		// fire listeners
	}
	
	// TODO implement on server side and client side, but differently
//	public void fireListeners() {
//		
//	}
	
	public String getState() {
		return state;
	}
	
	public Integer getRound() {
		return round;
	}
	
	public String getWordGiven() {
		return wordGiven;
	}
	
	public ArrayList<String> getDescribingWords() {
		return describingWords;
	}
	
	public ArrayList<String> getUsernames() {
		return usernames;
	}
}
