package com.perniktv.serverjam.managers;

import java.util.HashMap;
import java.util.UUID;

import com.perniktv.serverjam.data.PlayerData;

public class PlayerDataManager extends Manager {

	private HashMap<UUID, PlayerData> players;

	@Override
	public void init() {
		this.players = new HashMap<UUID, PlayerData>();
		
	}

	@Override
	public void teardown() {

	}

}
