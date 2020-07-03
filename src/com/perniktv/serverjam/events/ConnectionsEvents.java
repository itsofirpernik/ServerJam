package com.perniktv.serverjam.events;

import com.perniktv.serverjam.data.PlayerData;
import com.perniktv.serverjam.utils.SchematicLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.perniktv.serverjam.managers.PlayerDataManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ConnectionsEvents implements Listener {
	

	@EventHandler
	public void login(PlayerLoginEvent event) {
		PlayerDataManager.getInstance().addPlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		PlayerDataManager.getInstance().addPlayer(event.getPlayer().getUniqueId());
		PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(event.getPlayer().getUniqueId());
		if (playerData.getCampLocation() == null) {
			World world = Bukkit.getServer().getWorld("world");
			int centerX = 1500, centerY = 95, centerZ = 1500;
			Location center = new Location(world, centerX, centerY, centerZ);
			SchematicLoader.loadSchematic(world, center, "spawn_area_test.schem");
			playerData.setCampLocation(center);
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		PlayerDataManager.getInstance().removePlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void kick(PlayerKickEvent event) {
		PlayerDataManager.getInstance().removePlayer(event.getPlayer().getUniqueId());
	}
}
