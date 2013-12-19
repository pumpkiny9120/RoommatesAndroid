package com.oose2013.group7.roommates;

/**
 * The Class GameEvent.
 */
public class GameEvent {
	
	/** The model. */
	private GameModel model;
	
	/**
	 * Instantiates a new game event.
	 *
	 * @param model the model
	 */
	public GameEvent (GameModel model){
		this.model = model;
	}
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public GameModel getModel() {
		return model;
	}
	
}
