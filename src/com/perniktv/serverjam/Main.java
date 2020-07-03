package com.perniktv.serverjam;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.perniktv.serverjam.events.NoAchievements;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	private Logger log = Logger.getLogger("minecraft");
	
	@Override
	public void onEnable() {
		plugin = this;
		registerCommands();
		registerEvents();
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		plugin = null;
		super.onDisable();
	}
	
	private void registerCommands() {
		
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new NoAchievements(), this);
	}
	
	public static Main getPlugin() {
		return plugin;
	}

}
