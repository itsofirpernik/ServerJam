package com.perniktv.serverjam.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.perniktv.serverjam.data.PlayerData;
import com.perniktv.serverjam.managers.PlayerDataManager;
import com.perniktv.serverjam.utils.ItemUtils;

import net.md_5.bungee.api.ChatColor;

public class PlayerGUI {

	private static final int INVENTORY_ONE_LINE_SIZE = 9;

	private static final String PLAYER_MENU_TITLE = "Player Menu";

	public static final Inventory getPlayerMenu(Player player, Player target) {
		/*
		 * ^ == Add friend - == Player Head -***^****
		 * 
		 * -*+*^*.*_
		 */

		Inventory inventory = Bukkit.createInventory(null, INVENTORY_ONE_LINE_SIZE, PLAYER_MENU_TITLE);

		if (null == inventory) {
			return null;
		}

		PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(player);

		if (player.getUniqueId().equals(target.getUniqueId())) {
			// Self Menu
			ItemStack playerHead = ItemUtils.createSkull(player.getName());
			playerHead = ItemUtils.createItem(playerHead, 0, ChatColor.AQUA + player.getName(),
					Arrays.asList("Your Stats", ""));
			return inventory;
		}
		if (!playerData.isFriend(target.getUniqueId())) {

			return inventory;
		}

		// PlayerData targetData =
		// PlayerDataManager.getInstance().getPlayerData(target);

		return inventory;

	}

}
