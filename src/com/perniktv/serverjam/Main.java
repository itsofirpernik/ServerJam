package com.perniktv.serverjam;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.perniktv.serverjam.commands.CampCommand;
import com.perniktv.serverjam.commands.CommandManager;
import com.perniktv.serverjam.events.ConnectionsEvents;
import com.perniktv.serverjam.events.NoAchievements;
import com.perniktv.serverjam.managers.ItemDataManager;
import com.perniktv.serverjam.managers.PlayerDataManager;

public class Main extends JavaPlugin {

	public static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		registerCommands();
		registerEvents();
		loadManagers();

		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerDataManager.getInstance().addPlayer(player.getUniqueId());
		}

		super.onEnable();
	}

	@Override
	public void onDisable() {
		unloadManagers();

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

	private void loadManagers() {
		PlayerDataManager.getInstance().init();
		ItemDataManager.getInstance().init();
	}

	private void unloadManagers() {
		PlayerDataManager.getInstance().teardown();
		ItemDataManager.getInstance().teardown();
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
