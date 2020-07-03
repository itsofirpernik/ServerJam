package com.perniktv.serverjam.commands;

import com.perniktv.serverjam.commands.annotations.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class CampCommand {

	@CommandHandler(name="camp")
	public void camp(Player player, String[] args) {
		player.sendMessage("Hello world!");
		World world = player.getWorld();
		System.out.println(world.getName());
		for (int x = 150; x < 200; x++) {
			for (int y = 70; y < 90; y++) {
				for (int z = 150; z < 200; z++) {
					world.getBlockAt(x, y, z).setType(Material.DIAMOND_BLOCK);
				}
			}
		}
	}

}
