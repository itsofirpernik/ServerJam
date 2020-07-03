package com.perniktv.serverjam.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStats {
	private int power;
	private int minDamage;
	private int maxDamage;
	private int critDamage;
	private int critChance;
	private List<ItemAbility> itemAbilities;

	public ItemStats(ItemStack item) {
		init();
		if (!item.hasItemMeta()) {
			return;
		}
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) {
			return;
		}
		this.deserialize(meta.getLore());
	}

	public ItemStats(List<String> lore) {
		init();
		this.deserialize(lore);
	}

	public void init() {
		this.power = 0;
		this.minDamage = 0;
		this.maxDamage = 0;
		this.critDamage = 0;
		this.critChance = 0;
		this.itemAbilities = null;
	}

	public List<String> serialize() {
		List<String> lore = new ArrayList<String>();

		if (0 != this.power) {
			lore.add("Power" + ": " + this.power);
		}
		if (0 != this.minDamage) {
			lore.add("Min Damage" + ": " + this.minDamage);
		}
		if (0 != this.maxDamage) {
			lore.add("Max Damage" + ": " + this.maxDamage);
		}
		if (0 != this.critDamage) {
			lore.add("Crit Damage" + ": " + this.critDamage);
		}
		if (0 != this.critChance) {
			lore.add("Crit Chance" + ": " + this.critChance + "%");
		}
		if (null != this.itemAbilities) {

		}

		return lore;

	}

	public void deserialize(List<String> lore) {
		if (null == lore) {
			return;
		}

		for (String line : lore) {
			line = ChatColor.stripColor(line);
			if (!line.contains(":")) {
				continue;
			}
			String[] stat = line.split(":");
			if (stat.length != 2) {
				continue;
			}
			if ("Power".equals(stat[0])) {
				this.power = Integer.parseInt(stat[1].trim());
			} else if ("Min Damage".equals(stat[0])) {
				this.minDamage = Integer.parseInt(stat[1].trim());
			} else if ("Max Damage".equals(stat[0])) {
				this.maxDamage = Integer.parseInt(stat[1].trim());
			} else if ("Crit Damage".equals(stat[0])) {
				this.critDamage = Integer.parseInt(stat[1].trim());
			} else if ("Crit Chance".equals(stat[0])) {
				this.critChance = Integer.parseInt(stat[1].trim().replace("%", ""));
			}
		}
	}

	public int getPower() {
		return power;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public int getCritDamage() {
		return critDamage;
	}

	public int getCritChance() {
		return critChance;
	}

	public List<ItemAbility> getAbilities() {
		return itemAbilities;
	}
}
