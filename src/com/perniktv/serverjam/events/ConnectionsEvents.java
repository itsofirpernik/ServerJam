package com.perniktv.serverjam.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionsEvents implements Listener {
	

	@EventHandler
	public void login(PlayerLoginEvent event) {
		
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		
	}
	
	@EventHandler
	public void kick(PlayerKickEvent event) {
		
	}
	
	
	
}
