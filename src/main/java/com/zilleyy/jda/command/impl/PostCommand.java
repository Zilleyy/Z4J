package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.bridge.Rest;
import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.ExecutionStatus;

/**
 * Author: Zilleyy
 * <br>
 * Date: 1/04/2021 @ 2:14 pm AEST
 */
public class PostCommand extends Command {

    public PostCommand() {
        super("Send post requests to the rest API", "post");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        Rest.post(information.getArgs()[0]);
        return ExecutionStatus.GENERIC_SUCCESS;
    }

}
