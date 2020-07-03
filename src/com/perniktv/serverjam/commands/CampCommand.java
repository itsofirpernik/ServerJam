package com.perniktv.serverjam.commands;

import com.perniktv.serverjam.commands.annotations.CommandHandler;
import org.bukkit.entity.Player;

public class CampCommand {

	@CommandHandler(name="camp")
	public void camp(Player player, String[] args) {
		player.sendMessage("Hello world!");
	}

}
