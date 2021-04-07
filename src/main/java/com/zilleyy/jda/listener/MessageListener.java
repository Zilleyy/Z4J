package com.zilleyy.jda.listener;

import com.zilleyy.jda.Configuration;
import com.zilleyy.jda.bridge.ParameterBuilder;
import com.zilleyy.jda.bridge.RequestQueue;
import com.zilleyy.jda.command.CommandManager;
import com.zilleyy.jda.command.ExecutionStatus;
import com.zilleyy.jda.console.ConsoleLogger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Zilleyy
 * <br>
 * Date: 25/03/2021 @ 3:15 pm AEST
 */
public class MessageListener extends ListenerAdapter {

    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // The message instance that has been received.
        Message message = event.getMessage();

        // Drop out if the user who sent the message is this bot. (stop feedback loops and wasting resources etc.)
        if(message.getAuthor().isBot()) return;

        // Send the message to the RestAPI
        this.rest(message);

        // Drop out if the message does not start with the command prefix.
        if(message.getContentRaw().toCharArray()[0] != Configuration.INSTANCE.getPrefix().charAt(0)) return;

        // Check if the command is valid.
        if(CommandManager.isRegistered(message)) {
            if(CommandManager.getCommand(message).getScope().matches(message)) {
                ExecutionStatus status = ExecutionStatus.GENERIC_FAILURE;
                try {
                    status = CommandManager.execute(message);
                } catch(Exception exception) {
                    ConsoleLogger.error(exception);
                }
                // Check if the command execution was successful, if it was then return.
                if(status.getBaseStatus() != ExecutionStatus.GENERIC_FAILURE) return;
                else message.reply("An unhandled error occurred - " + status + ":" + status.ordinal()).queue();
            } else message.reply("You cannot use this command here...");
        } else event.getMessage().reply("Unknown command, type /help.").queue();
    }

    private void rest(Message message) {
        RequestQueue.add(new ParameterBuilder("/messages").add("username", message.getAuthor().getName()).add("content", message.getContentRaw().replaceAll("\n", "\n")).add("mid", message.getId()).add("uid", message.getAuthor().getId()).build());
    }

}
