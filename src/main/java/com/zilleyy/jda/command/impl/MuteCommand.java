package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.CommandScope;
import com.zilleyy.jda.command.ExecutionStatus;
import com.zilleyy.jda.console.ConsoleLogger;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

/**
 * Author: Zilleyy
 * <br>
 * Date: 6/05/2021 @ 11:42 am AEST
 */
public class MuteCommand extends Command {

    public MuteCommand() {
        super("Mutes the specified user", CommandScope.GUILD, "mute");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        if(!information.hasArguments()) return ExecutionStatus.INCORRECT_ARGUMENTS;

        for(Member member : information.getMessage().getGuild().getMembers()) {

        }
        ConsoleLogger.log(information.getArgs()[0]);

        return ExecutionStatus.GENERIC_SUCCESS;
    }

    /**
     * If it exists already, then find it and return it, otherwise create it and return it.
     * @return the mute role.
     */
    private Role getMuteRole() {
        return null;
    }

}
