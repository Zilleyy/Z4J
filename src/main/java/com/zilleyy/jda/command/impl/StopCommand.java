package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.Engine;
import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.ExecutionStatus;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 10:59 pm AEST
 */
public class StopCommand extends Command {

    public StopCommand() {
        super("Shuts down the bot.", "stop", "shutdown");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        Engine.getInstance().stop();
        return ExecutionStatus.GENERIC_SUCCESS;
    }

}
