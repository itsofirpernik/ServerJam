package com.perniktv.serverjam.utils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

	public static ItemStack createItem(Material material, int durability, String displayname, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (durability > 0) {
			((Damageable) meta).setDamage(durability);
		}
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
		if (null != lore) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack createItem(ItemStack itemStack, int durability, String displayname, List<String> lore) {
		ItemStack item = itemStack.clone();
		ItemMeta meta = item.getItemMeta();
		if (durability > 0) {
			((Damageable) meta).setDamage(durability);
		}
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
		if (null != lore) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);

		return item;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createSkull(String owner) {
		ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(owner);
		item.setItemMeta(meta);

		return item;
	}

}
