package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.CommandScope;
import com.zilleyy.jda.command.ExecutionStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 12:04 pm AEST
 */
public class PurgeCommand extends Command {

    public PurgeCommand() {
        super("Purges messages in the channel", CommandScope.GUILD, "purge");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        MessageChannel channel = information.getMessage().getChannel();
        MessageHistory history = new MessageHistory(channel);

        if(information.getArgs()[0].equals("all")) {
            ChannelAction channelAction = information.getMessage().getTextChannel().createCopy();
            information.getMessage().getTextChannel().delete().queue();
            TextChannel textChannel = (TextChannel) channelAction.complete();

            textChannel.sendMessage("Nuked " + textChannel.getAsMention()).addFile(new File("src/main/resources/nuke.gif")).queue(success -> success.delete().queueAfter(3, TimeUnit.SECONDS));
        } else {
            history.retrievePast(1).complete().get(0).delete().queue();
            List<Message> messages = history.retrievePast(Integer.parseInt(information.getArgs()[0])).complete();
            channel.purgeMessages(messages);
        }
        return ExecutionStatus.GENERIC_SUCCESS;
    }

}
