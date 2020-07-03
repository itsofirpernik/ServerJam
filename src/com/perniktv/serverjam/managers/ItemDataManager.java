package com.perniktv.serverjam.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.perniktv.serverjam.Main;
import com.perniktv.serverjam.data.ItemData;

public class ItemDataManager extends Manager {
	
	private List<ItemData> items;
	
	@Override
	public void init() {
		this.items = new ArrayList<ItemData>();
		File itemsDirectory = new File(Main.getPlugin().getDataFolder() + File.pathSeparator + "items");
		if (!itemsDirectory.exists()) {
			itemsDirectory.mkdirs();
		}
		
		for (File file : itemsDirectory.listFiles()) {
			String filename = file.getName();
			filename = filename.replace(".yaml", "");
			this.items.add(new ItemData(filename));
		}
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}

}
