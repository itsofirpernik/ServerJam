package com.perniktv.serverjam.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.perniktv.serverjam.Main;
import com.perniktv.serverjam.utils.DifficultyType;

public class PlayerData {
	private UUID uuid;
	private int power;
	private int exp;
	private int coins;
	private List<UUID> friends;
	private HashMap<String, List<DifficultyType>> maps;
	private List<String> runes;
	private Location campLocation;

	public PlayerData(UUID uuid) {
		this.uuid = uuid;

		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

		this.init(player);
		this.initCamp();
	}

	public UUID getUniqueId() {
		return this.uuid;
	}

	public String getName() {
		OfflinePlayer player = getOfflinePlayer();
		if (null == player) {
			return null;
		}

		return player.getName();
	}

	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(this.uuid);
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}

	public int getPower() {
		return this.power;
	}

	public int getExp() {
		return this.exp;
	}

	public int getCoins() {
		return this.coins;
	}

	public List<UUID> getFriends() {
		return this.friends;
	}

	public HashMap<String, List<DifficultyType>> getMaps() {
		return this.maps;
	}

	public List<String> getRunes() {
		return this.runes;
	}

	public Location getCampLocation() {
		return this.campLocation;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void depositCoins(int coins) {
		this.coins += coins;
	}

	public boolean withdrawCoins(int coins) {
		if (coins > this.coins) {
			return false;
		}
		this.coins -= coins;
		return true;
	}

	public void setFriends(List<UUID> friends) {
		this.friends.clear();
		for (UUID friend : friends) {
			this.friends.add(friend);
		}
	}

	public boolean addFriend(UUID friend) {
		if (friends.contains(friend)) {
			return false;
		}

		return this.friends.add(friend);
	}

	public boolean removeFriend(UUID friend) {
		if (!friends.contains(friend)) {
			return false;
		}
		return friends.remove(uuid);
	}

	public void setMaps(HashMap<String, List<DifficultyType>> maps) {
		this.maps.clear();
		for (String map : maps.keySet()) {
			this.maps.put(map, maps.get(map));
		}
	}

	public void addMap(String map, DifficultyType difficulty) {
		if (this.maps.containsKey(map) && this.maps.get(map).contains(difficulty)) {
			return;
		}
	}

	public void removeMap(String map) {
		if (!this.maps.containsKey(map)) {
			return;
		}
		this.maps.remove(map);
	}

	public void removeMap(String map, DifficultyType difficulty) {
		if (!this.maps.containsKey(map)) {
			return;
		}
		if (!this.maps.get(map).contains(difficulty)) {
			return;
		}
		this.maps.get(map).remove(difficulty);
	}

	public void setRunes(List<String> runes) {
		this.runes.clear();
		for (String rune : this.runes) {
			this.runes.add(rune);
		}
	}

	public void addRune(String rune) {
		if (this.runes.contains(rune)) {
			return;
		}

		this.runes.add(rune);
	}

	public void removeRune(String rune) {
		if (!this.runes.contains(rune)) {
			return;
		}

		this.runes.remove(rune);
	}

	public void setCampLocation(Location campLocation) {
		this.campLocation = campLocation.clone();
	}

	private void init(OfflinePlayer player) {
		this.power = 1;
		this.exp = 0;
		this.coins = 0;
		this.friends = new ArrayList<UUID>();
		this.maps = new HashMap<String, List<DifficultyType>>();
		this.runes = new ArrayList<String>();
		this.campLocation = null;

		if (!Main.getPlugin().getConfig().contains("players")) {
			Main.getPlugin().getConfig().createSection("players");
			Main.getPlugin().saveConfig();
		}

		ConfigurationSection players = Main.getPlugin().getConfig().getConfigurationSection("players");
		if (!players.contains(this.uuid.toString().toString())) {
			players.set(this.uuid.toString() + "." + "name", player.getName());
			players.set(this.uuid.toString() + "." + "power", this.power);
			players.set(this.uuid.toString() + "." + "exp", this.exp);
			players.set(this.uuid.toString() + "." + "coins", this.coins);
			players.set(this.uuid.toString() + "." + "friends", null);
			players.set(this.uuid.toString() + "." + "maps", null);
			players.set(this.uuid.toString() + "." + "runes", null);

			Main.getPlugin().saveConfig();
			return;
		}

		ConfigurationSection playerSection = players.getConfigurationSection(this.uuid.toString());

		this.power = playerSection.getInt("power");
		this.exp = playerSection.getInt("exp");
		this.coins = playerSection.getInt("coins");
		if (playerSection.contains("friends")) {
			List<String> friendList = playerSection.getStringList("friends");
			for (String friend : friendList) {
				this.friends.add(UUID.fromString(friend));
			}
		}

		if (playerSection.contains("maps")) {
			ConfigurationSection mapSection = playerSection.getConfigurationSection("maps");
			for (String map : mapSection.getKeys(false)) {
				List<String> difficulties = mapSection.getStringList(map);
				List<DifficultyType> difficulitesTypes = new ArrayList<DifficultyType>();
				for (String difficulty : difficulties) {
					difficulitesTypes.add(DifficultyType.valueOf(difficulty));
				}
				this.maps.put(map, difficulitesTypes);
			}
		}

		if (playerSection.contains("runes")) {
			this.runes = playerSection.getStringList("runes");
		}
	}

	private void initCamp() {
		if (!Main.getPlugin().getConfig().contains("camps")) {
			Main.getPlugin().getConfig().createSection("camps");
			Main.getPlugin().saveConfig();
		}

		ConfigurationSection camps = Main.getPlugin().getConfig().getConfigurationSection("camps");
		if (!camps.contains(this.uuid.toString())) {
			camps.set(this.uuid.toString() + "." + "world", "");
			camps.set(this.uuid.toString() + "." + "x", 0);
			camps.set(this.uuid.toString() + "." + "y", 0);
			camps.set(this.uuid.toString() + "." + "z", 0);
			camps.set(this.uuid.toString() + "." + "yaw", 0);
			camps.set(this.uuid.toString() + "." + "pitch", 0);

			Main.getPlugin().saveConfig();
			return;
		}

		ConfigurationSection camp = camps.getConfigurationSection(this.uuid.toString());

		World world = Bukkit.getWorld(camp.getString("world"));
		if (null == world) {
			return;
		}

		campLocation = new Location(world, camp.getDouble("x"), camp.getDouble("y"), camp.getDouble("z"),
				(float) camp.getDouble("yaw"), (float) camp.getDouble("pitch"));
	}

	public void save() {
		if (!Main.getPlugin().getConfig().contains("players")) {
			Main.getPlugin().getConfig().createSection("players");
			Main.getPlugin().saveConfig();
		}

		ConfigurationSection players = Main.getPlugin().getConfig().getConfigurationSection("players");
		players.set(this.uuid.toString() + "." + "name", this.getName());
		players.set(this.uuid.toString() + "." + "power", this.power);
		players.set(this.uuid.toString() + "." + "exp", this.exp);
		players.set(this.uuid.toString() + "." + "coins", this.coins);
		players.set(this.uuid.toString() + "." + "friends", this.friends);
		players.set(this.uuid.toString() + "." + "maps", null);
		for (String map : this.maps.keySet()) {
			players.set(this.uuid.toString() + "." + "maps" + "." + map, this.maps.get(map));
		}
		players.set(this.uuid.toString() + "." + "runes", this.runes);

		Main.getPlugin().saveConfig();
		
		ConfigurationSection camps = Main.getPlugin().getConfig().getConfigurationSection("camps");
		camps.set(this.uuid.toString() + "." + "world", campLocation.getWorld().getName());
		camps.set(this.uuid.toString() + "." + "x", campLocation.getX());
		camps.set(this.uuid.toString() + "." + "y", campLocation.getY());
		camps.set(this.uuid.toString() + "." + "z", campLocation.getZ());
		camps.set(this.uuid.toString() + "." + "yaw", campLocation.getYaw());
		camps.set(this.uuid.toString() + "." + "pitch", campLocation.getPitch());

		Main.getPlugin().saveConfig();
	}
}
