package com.perniktv.serverjam.commands;

import com.google.common.collect.Lists;
import com.perniktv.serverjam.commands.annotations.CommandHandler;
import com.perniktv.serverjam.utils.CommandMethodSorter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final static List<Command> commands = new ArrayList<>();

    public static void registerCommands(JavaPlugin plugin, Object handler) {
        List<Method> methods = new ArrayList<>();
        for (Method method : handler.getClass().getDeclaredMethods()) {
            if (isCommandMethod(method) && isCommandHandler(method)) {
                methods.add(method);
            }
        }

        methods.sort(new CommandMethodSorter());
        for (Method method : methods) {
            CommandHandler commandHandler = method.getAnnotation(CommandHandler.class);
            Command command = new Command(commandHandler.name(), handler, method);
            command.aliases.addAll(Lists.newArrayList(commandHandler.aliases()));
            if (!commandHandler.description().isEmpty()) {
                command.description = commandHandler.description();
            }

            if (!commandHandler.usage().isEmpty()) {
                command.usage = commandHandler.usage();
            }

            if (!commandHandler.permission().isEmpty()) {
                command.permission = commandHandler.permission();
            }

            if (!commandHandler.noPermissionMessage().isEmpty()) {
                command.noPermissionMessage = commandHandler.noPermissionMessage();
            }

            if (commandHandler.parent().length > 0) {
                command.parents = commandHandler.parent();
                String[] parent = new String[commandHandler.parent().length - 1];
                System.arraycopy(commandHandler.parent(), 1, parent, 0, parent.length);

                for (Command baseCommand : commands) {
                    if (baseCommand.shouldRun(commandHandler.parent()[0])) {
                        baseCommand.addSubCommand(command, parent);
                    }
                }
            } else {
                commands.add(command);
                PluginCommand pluginCommand = plugin.getCommand(command.name);
                if (pluginCommand != null) {
                    pluginCommand.setUsage(command.usage);
                    pluginCommand.setAliases(command.aliases);
                }
            }
        }
    }

    private static boolean isCommandMethod(Method method) {
        Class<?>[] params = method.getParameterTypes();
        return params.length == 2 && CommandSender.class.isAssignableFrom(params[0]) && String[].class.equals(params[1]);
    }


    private static boolean isCommandHandler(Method method) {
        return method.isAnnotationPresent(CommandHandler.class);
    }

    public static boolean executeCommand(CommandSender sender, org.bukkit.command.Command commandToRun, String[] args) {
        for (Command command : commands) {
            if (command.shouldRun(commandToRun.getName())) {
                return command.execute(sender, args);
            }
        }

        sender.sendMessage(ChatColor.RED + "Unknown command. Please use 'help' to find the command you were looking for.");
		return false;
	}
}
