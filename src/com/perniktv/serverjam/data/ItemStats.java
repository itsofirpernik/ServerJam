package com.perniktv.serverjam.data;

import java.util.List;

import org.bukkit.ChatColor;

public class ItemStats {
	private int power;
	private int minDamage;
	private int maxDamage;
	private int critDamage;
	private int critChance;
	private List<ItemAbility> itemAbilities;

	public ItemStats(List<String> lore) {
		this.power = -1;
		this.minDamage = -1;
		this.maxDamage = -1;
		this.critDamage = -1;
		this.critChance = -1;
		this.itemAbilities = null;
		
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
				this.critChance = Integer.parseInt(stat[1].trim());
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
