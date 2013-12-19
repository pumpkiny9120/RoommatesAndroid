package com.oose2013.group7.roommates;

/**
 * The listener interface for receiving game events.
 * The class that is interested in processing a game
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGameListener<code> method. When
 * the game event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GameEvent
 */
public interface GameListener {

	/**
	 * Method to update the current model on the client side.
	 *
	 * @param event the event
	 */
	public void modelChanged(GameEvent event);
}
