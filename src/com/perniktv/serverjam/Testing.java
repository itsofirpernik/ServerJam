package com.perniktv.serverjam;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.perniktv.serverjam.utils.DifficultyType;

public class Testing {
	public static void main(String[] args) {
		Material material = Material.getMaterial("COBBLESTONE");
		ItemStack item = new ItemStack(material);
		
		List<DifficultyType> difficulites = new ArrayList<DifficultyType>();
		
		difficulites.add(DifficultyType.ADVENTURE);
		difficulites.add(DifficultyType.DEFAULT);
		
		System.out.println(difficulites);
		
		// System.out.println(material.toString());
		// System.out.println(material.getKey().toString());
	}
}
