package com.perniktv.serverjam.events;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class NoAchievements implements Listener {

	@EventHandler
	public void onAchievement(PlayerAdvancementDoneEvent event) {
		Player player = event.getPlayer();
		Advancement advancement = event.getAdvancement();

		for(String s : advancement.getCriteria()) 
			player.getAdvancementProgress(advancement).revokeCriteria(s);

	}

}