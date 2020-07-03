package com.perniktv.serverjam.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.perniktv.serverjam.managers.PlayerDataManager;

public class ConnectionsEvents implements Listener {
	

	@EventHandler
	public void login(PlayerLoginEvent event) {
		PlayerDataManager.getInstance().addPlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		PlayerDataManager.getInstance().addPlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		PlayerDataManager.getInstance().removePlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void kick(PlayerKickEvent event) {
		PlayerDataManager.getInstance().addPlayer(event.getPlayer().getUniqueId());
	}
	
	
	
}
