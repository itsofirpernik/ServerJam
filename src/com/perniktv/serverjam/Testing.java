package com.perniktv.serverjam;

import org.bukkit.Material;

public class Testing {
	public static void main(String[] args) {
		Material material = Material.getMaterial("COBBLESTONE");
		System.out.println(material.toString());
		System.out.println(material.getKey().toString());
	}
}
