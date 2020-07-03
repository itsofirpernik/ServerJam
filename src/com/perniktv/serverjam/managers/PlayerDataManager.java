package com.perniktv.serverjam.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.perniktv.serverjam.Main;
import com.perniktv.serverjam.data.PlayerData;

public class PlayerDataManager extends Manager {

	private static final PlayerDataManager playerDataManager = new PlayerDataManager();

	private List<PlayerData> players;

	@Override
	public void init() {
		this.players = new ArrayList<PlayerData>();

		if (!Main.getPlugin().getConfig().contains("players")) {
			Main.getPlugin().getConfig().createSection("players");
			Main.getPlugin().saveConfig();
		}

		ConfigurationSection players = Main.getPlugin().getConfig().getConfigurationSection("players");

		for (String uuid : players.getKeys(false)) {
			this.players.add(new PlayerData(UUID.fromString(uuid)));
		}
	}

	@Override
	public void teardown() {
		for (PlayerData playerData : players) {
			playerData.save();
		}
		Main.getPlugin().saveConfig();
	}

	public PlayerData getPlayerData(UUID uuid) {
		for (PlayerData playerData : players) {
			if (playerData.getUniqueId().equals(uuid)) {
				return playerData;
			}
		}

		return null;
	}

	public PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getUniqueId());
	}

	public PlayerData getPlayerData(OfflinePlayer player) {
		return getPlayerData(player.getUniqueId());
	}

	public boolean addPlayer(UUID uuid) {
		if (null != getPlayerData(uuid)) {
			return false;
		}
		PlayerData playerData = new PlayerData(uuid);
		this.players.add(playerData);
		return true;
	}

	public boolean removePlayer(UUID uuid) {
		for (PlayerData playerData : players) {
			if (!playerData.getUniqueId().equals(uuid)) {
				continue;
			}
			playerData.save();
			players.remove(playerData);
			return true;
		}

		return false;

	}

	public static final PlayerDataManager getInstance() {
		return playerDataManager;
	}

}
