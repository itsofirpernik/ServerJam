package com.perniktv.serverjam.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Command {
	public final String name;
	public final List<String> aliases;
	public String description;
	public String usage;
	public String permission;
	public String noPermissionMessage;
	public final List<Command> subCommands;
	public String[] parents;

	private final Object handler;
	private final Method method;

	public Command(String name, Object handler, Method method) {
		this.name = name;
		this.handler = handler;
		this.method = method;

		this.aliases = new ArrayList<>();
		this.description = null;
		this.usage = null;
		this.permission = null;
		this.noPermissionMessage = null;
		this.subCommands = new ArrayList<>();
		this.parents = null;
	}

	public boolean shouldRun(String name) {
		return this.name.equals(name) || this.aliases.contains(name);
	}

	public Command getSubCommand(String name) {
		for (Command subCommand : this.subCommands) {
			if (subCommand.shouldRun(name)) {
				return subCommand;
			}
		}

		return null;
	}

	public boolean hasSubCommand(String name) {
		return this.getSubCommand(name) != null;
	}

	public boolean hasSubCommand(Command subCommand) {
		return this.subCommands.contains(subCommand);
	}

	public boolean addSubCommand(Command newSubCommand, String[] parents) {
		if (parents.length > 0) {
			for (Command subCommand : this.subCommands) {
				if (subCommand.shouldRun(parents[0])) {
					String[] parent = new String[parents.length - 1];
					System.arraycopy(parents, 1, parent, 0, parent.length);
					return subCommand.addSubCommand(newSubCommand, parent);
				}
			}

			return false;
		}

		if (this.hasSubCommand(newSubCommand.name)) {
			return false;
		}

		this.subCommands.add(newSubCommand);
		return true;
	}

	public boolean removeSubCommand(Command subCommand) {
		if (this.hasSubCommand(subCommand)) {
			this.subCommands.remove(subCommand);
			return true;
		}

		return false;
	}

	public boolean execute(CommandSender commandSender, String[] args) {
		if (args.length > 0) {
			String subCommandName = args[0];
			if (subCommandName.equalsIgnoreCase("help")) {
				return this.executeHelp(commandSender);
			} else if (this.hasSubCommand(subCommandName)) {
				Command subCommand = this.getSubCommand(subCommandName);
				String[] subArgs = new String[args.length - 1];
				System.arraycopy(args, 1, subArgs, 0, args.length - 1);

				return subCommand.execute(commandSender, subArgs);
			}
		}

		if (this.method.getParameterTypes()[0].equals(Player.class) && !(commandSender instanceof Player)) {
			commandSender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
			return true;
		} else if (this.method.getParameterTypes()[0].equals(ConsoleCommandSender.class)
				&& !(commandSender instanceof ConsoleCommandSender)) {
			commandSender.sendMessage(ChatColor.RED + "This command can only be run by a console user.");
			return true;
		}

		if (this.permission != null && !this.permission.isEmpty() && !commandSender.hasPermission(this.permission)) {
			if (this.noPermissionMessage != null && !this.noPermissionMessage.isEmpty()) {
				commandSender.sendMessage(this.noPermissionMessage);
			} else {
				commandSender.sendMessage(ChatColor.RED + "You dont have permission to run this command.");
			}

			return true;
		}

		try {
			this.method.invoke(this.handler, commandSender, args);
		} catch (Exception e) {
			commandSender.sendMessage(ChatColor.RED + "An error occurred while trying to process the command.");
			e.printStackTrace();
		}
		return true;
	}

	public boolean executeHelp(CommandSender commandSender) {
		if (this.usage != null) {
			commandSender.sendMessage(ChatColor.GOLD + "Usage: " + ChatColor.RESET + this.usage);
		} else {
			commandSender.sendMessage(ChatColor.RED + "This command has no usage.");
		}

		if (!this.subCommands.isEmpty()) {
			commandSender.sendMessage(ChatColor.GOLD + "Sub Commands:");
			for (Command command : this.subCommands) {
				if (command.permission != null && !command.permission.isEmpty()
						&& !commandSender.hasPermission(command.permission)) {
					continue;
				}

				String commandName = command.name;
				if (command.parents != null && command.parents.length > 0) {
					commandName = String.join(" ", command.parents) + " " + commandName;
				}

				String usage = "";
				if (command.usage != null) {
					usage = ChatColor.RESET + ": " + ChatColor.GOLD + command.usage;
				}

				commandSender.sendMessage(ChatColor.RED + "/" + commandName + usage);
			}
		}

		return true;
	}
}
