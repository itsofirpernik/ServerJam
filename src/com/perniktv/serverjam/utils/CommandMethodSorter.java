package com.perniktv.serverjam.utils;

import com.perniktv.serverjam.commands.annotations.CommandHandler;

import java.lang.reflect.Method;
import java.util.Comparator;

public class CommandMethodSorter implements Comparator<Method> {

    @Override
    public int compare(Method o1, Method o2) {
        CommandHandler commandHandler1 = o1.getAnnotation(CommandHandler.class);
        CommandHandler commandHandler2 = o2.getAnnotation(CommandHandler.class);

        // I want to check the length of the parent and if it is the same, I want to sort by name.
        int lenDiff = commandHandler1.parent().length - commandHandler2.parent().length;
        if (lenDiff != 0) {
            return lenDiff;
        }

        return commandHandler1.name().compareTo(commandHandler2.name());
    }
}
