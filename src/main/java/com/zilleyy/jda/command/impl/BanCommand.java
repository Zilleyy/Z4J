package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.CommandScope;
import com.zilleyy.jda.command.ExecutionStatus;
import net.dv8tion.jda.api.Permission;

import static com.zilleyy.jda.command.ExecutionStatus.NO_PERMISSION;

/**
 * Author: Zilleyy
 * <br>
 * Date: 4/04/2021 @ 9:41 am AEST
 */
public class BanCommand extends Command {

    public BanCommand() {
        super("Bans a user", CommandScope.GUILD, "ban");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        if(!information.getMessage().getMember().hasPermission(Permission.BAN_MEMBERS)) return NO_PERMISSION;

        return ExecutionStatus.GENERIC_SUCCESS;
    }
}
