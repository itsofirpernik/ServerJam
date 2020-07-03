package com.perniktv.serverjam.data;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.perniktv.serverjam.utils.DifficultyType;

public class PlayerData {
	private UUID uuid;
	private int level;
	private List<UUID> friends;
	private HashMap<String, List<DifficultyType>> maps;
	private List<String> runes;
	
	public PlayerData(UUID uuid) {
		
	}
}
