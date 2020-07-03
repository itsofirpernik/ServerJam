package com.perniktv.serverjam.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.perniktv.serverjam.commands.annotations.CommandHandler;
import com.perniktv.serverjam.data.PlayerData;
import com.perniktv.serverjam.managers.PlayerDataManager;

public class CampCommand {

	@CommandHandler(name = "camp")
	public void camp(Player player, String[] args) {
		PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(player);
		Location campLocation = playerData.getCampLocation();
		if (null == campLocation) {
			player.sendMessage(ChatColor.DARK_RED + "You have no camp!");
			return;
		}

		player.teleport(campLocation);

	}

}
