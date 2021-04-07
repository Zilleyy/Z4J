package com.zilleyy.jda.command;

import com.zilleyy.jda.Configuration;
import com.zilleyy.jda.console.ConsoleLogger;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

import static com.zilleyy.jda.console.ConsoleLogger.error;

/**
 * Author: Zilleyy
 * <br>
 * Date: 25/03/2021 @ 7:49 pm AEST
 */
public class CommandManager {

    private static Reflections reflections = new Reflections("com.zilleyy.jda");
    private static List<Command> commands = new ArrayList<>();

    @SneakyThrows
    public static void registerCommand(Command command) {
        commands.add(command);
        ConsoleLogger.getInstance().send("Registered command \"" + command.getClass().getSimpleName() + "\".");
    }

    public static boolean isRegistered(Message message) {
        return getCommand(message) != null;
    }

    @Nullable
    public static Command getCommand(Message message) {
        String content = message.getContentRaw();
        String label = (content.indexOf(' ') != -1 ? content.substring(0, content.indexOf(' ')) : content).replaceFirst(Configuration.INSTANCE.getPrefix(), "");
        for(Command command : commands) {
            for(String _label : command.getLabels()) {
                if(_label.equals(label)) return command;
            }
        }
        return null;
    }

    public static ExecutionStatus execute(Message message) {
        try {
            Command command = getCommand(message);
            if(command == null) return ExecutionStatus.GENERIC_FAILURE;
            return command.onCommand(compileCommandInformation(message));
        } catch(Exception exception) {
            error(exception);
            exception.printStackTrace();
            return ExecutionStatus.GENERIC_FAILURE;
        }
    }

    private static CommandInformation compileCommandInformation(Message message) {
        return new CommandInformation(message);
    }

    public static List<Command> getCommands() {
        return CommandManager.commands;
    }

    // Automatically instantiates every class that extends the Command class.
    static {
        for(Class<? extends Command> clazz : CommandManager.reflections.getSubTypesOf(Command.class)) {
            try {
                clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
