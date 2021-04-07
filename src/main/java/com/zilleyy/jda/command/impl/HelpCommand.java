package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.CommandManager;
import com.zilleyy.jda.command.ExecutionStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 10:50 am AEST
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("The default help command", "help", "?");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Help Commands").setDescription("Below are a list of all the commands").setFooter("The command prefix for this server is: /").setColor(0x97FD67);

        for(Command command : CommandManager.getCommands()) {
            builder.addField(new Field(command.getLabels()[0], command.getDescription(), true));
        }

        information.getMessage().getTextChannel().sendMessage(builder.build()).queue();
        return ExecutionStatus.GENERIC_SUCCESS;
    }

}
