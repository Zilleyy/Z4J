package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.Configuration;
import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.ExecutionStatus;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 11:28 am AEST
 */
public class TicketCommand extends Command {

    public TicketCommand() {
        super("Ticket command", "ticket", "tickets");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        information.getMessage().delete().queue();
        information.getMessage().getGuild().createTextChannel("ticket-" + Configuration.INSTANCE.getNextTicketID()).queue();
        return ExecutionStatus.GENERIC_SUCCESS;
    }

}
