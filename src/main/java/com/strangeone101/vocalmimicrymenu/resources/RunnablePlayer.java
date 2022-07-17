package com.strangeone101.vocalmimicrymenu.resources;

import org.bukkit.entity.Player;

public abstract class RunnablePlayer implements Runnable
{	
	public abstract void run(Player player);
	
	public void run() {System.out.println("RunnablePlayer has run run() method! This should not happen!");};
}
