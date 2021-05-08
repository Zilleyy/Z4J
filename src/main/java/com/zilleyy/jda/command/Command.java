package com.zilleyy.jda.command;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Author: Zilleyy
 * <br>
 * Date: 25/03/2021 @ 7:49 pm AEST
 */
public abstract class Command {

    @Getter private String description;
    @Getter private CommandScope scope;
    @Getter private String[] labels;

    /**
     * @param description the description of the command, this is displayed in the help menu.
     * @param scope the scope of the command. E.g. Server command, direct message command or both.
     * @param labels all the possible labels of the command a.k.a labels.
     */
    public Command(String description, CommandScope scope, String... labels) {
        this.description = description;
        this.scope = scope;
        this.labels = labels;

        CommandManager.registerCommand(this);
    }

    public Command(String description, String... labels) {
        this(description, CommandScope.BOTH, labels);
    }

    /**
     * @param information the information related to the executed command.
     * @return true if the command executed successfully or false if not.
     */
    public abstract ExecutionStatus onCommand(CommandInformation information);

    /**
     * Must override this method otherwise error will be unhandled.
     * @param information the information related to the executed command.
     */
    public void onError(CommandInformation information, ExecutionStatus status) {
        MessageEmbed embed = new EmbedBuilder().setTitle("Error")
                .setDescription("An unhandled negative ExecutionStatus was returned, this event has been logged.")
                .addField("Trace", status + " -> " + status.getParentStatus() + " -> " + status.getBaseStatus(), false)
                .build();
        information.getMessage().reply(embed).queue();
    }

}
