package com.perniktv.serverjam;

import java.util.logging.Logger;

import com.perniktv.serverjam.events.ConnectionsEvents;
import com.perniktv.serverjam.managers.ItemDataManager;
import com.perniktv.serverjam.managers.PlayerDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.perniktv.serverjam.commands.CommandManager;
import com.perniktv.serverjam.commands.CampCommand;
import com.perniktv.serverjam.events.NoAchievements;

public class Main extends JavaPlugin {

	public static Main plugin;
	private Logger log = Logger.getLogger("minecraft");

	@Override
	public void onEnable() {
		plugin = this;
		registerCommands();
		registerEvents();
		registerManagers();
		super.onEnable();
	}

	private void registerManagers() {
		PlayerDataManager.getInstance().init();
	}

	@Override
	public void onDisable() {
		plugin = null;
		super.onDisable();
	}

	private void registerCommands() {
		CommandManager.registerCommands(this, new CampCommand());
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new NoAchievements(), this);
		pm.registerEvents(new ConnectionsEvents(), this);
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return CommandManager.executeCommand(sender, command, args);
	}

	public static Main getPlugin() {
		return plugin;
	}

}
