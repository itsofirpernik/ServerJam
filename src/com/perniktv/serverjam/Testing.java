package com.perniktv.serverjam;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Testing {
	public static void main(String[] args) {
		Material material = Material.getMaterial("COBBLESTONE");
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		
		System.out.println(material.toString());
		System.out.println(material.getKey().toString());
	}
}
