package com.perniktv.serverjam.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.perniktv.serverjam.Main;

public class ItemData {
	private String name;
	private Material material;
	private String displayname;
	private List<String> lore;
	private ItemStack item;

	public ItemData(String name) {
		this.name = name;

		File itemFile = getItemFile();

		if (null == itemFile) {
			return;
		}

		YamlConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);

		if (!itemConfig.contains("material")) {
			return;
		}

		this.material = Material.getMaterial(itemConfig.getString("material"));
		this.displayname = itemConfig.getString("displayname", null);
		if (itemConfig.contains("lore")) {
			this.lore = itemConfig.getStringList("lore");
		} else {
			this.lore = null;
		}

		this.item = generateItem();
	}

	public ItemStack getItem() {
		return item;
	}

	private File getItemFile() {
		File itemsDirectory = new File(Main.getPlugin().getDataFolder() + File.pathSeparator + "items");
		if (!itemsDirectory.exists()) {
			itemsDirectory.mkdirs();
		}

		if (!itemsDirectory.isDirectory()) {
			itemsDirectory.delete();
			itemsDirectory.mkdirs();
		}

		File itemFile = new File(itemsDirectory.getPath() + File.pathSeparator + name + ".yaml");
		if (!itemFile.exists()) {
			try {
				itemFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		return itemFile;
	}

	private ItemStack generateItem() {
		ItemStack item = new ItemStack(this.material);
		if (null == this.displayname && null == this.lore) {
			return item;
		}

		ItemMeta itemMeta = item.getItemMeta();
		if (null != this.displayname) {
			itemMeta.setDisplayName(this.displayname);
		}

		if (null != this.lore) {
			itemMeta.setLore(lore);
		}
		
		item.setItemMeta(itemMeta);
		return item;
	}

}
