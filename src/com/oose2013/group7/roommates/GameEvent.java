package com.oose2013.group7.roommates;

public class GameEvent {
	private GameModel model;
	
	public GameEvent (GameModel model){
		this.model = model;
	}
	
	public GameModel getModel() {
		return model;
	}
	
}
