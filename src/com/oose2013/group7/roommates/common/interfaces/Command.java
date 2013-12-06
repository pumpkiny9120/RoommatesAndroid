package com.oose2013.group7.roommates.common.interfaces;

import com.oose2013.group7.roommates.common.commands.Context;


/***Command interface that is implemented by the all command objects*/
public interface Command {

	public void execute(Context context);
}
