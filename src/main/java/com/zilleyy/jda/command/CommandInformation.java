package com.zilleyy.jda.command;

import com.zilleyy.jda.Configuration;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

/**
 * Author: Zilleyy
 * <br>
 * Date: 25/03/2021 @ 7:54 pm AEST
 */
public class CommandInformation {

    private Message message;

    private String label;
    private String[] args;

    public CommandInformation(Message message) {
        this.message = message;
        this.compile();
    }

    public <T> T[] getArgs(Class<T> clazz) {
        return (T[]) this.args;
    }

    private void compile() {
        String content = this.message.getContentRaw();
        this.label = (content.indexOf(' ') != -1 ? content.substring(0, content.indexOf(' ')) : content).replaceFirst(Configuration.INSTANCE.getPrefix(), "");

        StringBuilder builder = new StringBuilder(content);
        builder.delete(0, this.label.length() + (content.contains(" ") ? 2 : 1));
        this.args = builder.toString().split(" ");
    }

    public boolean hasArguments() {
        return this.args.length > 0;
    }

    public Guild getGuild() {
        return this.message.getGuild();
    }

    public Message getMessage() {
        return this.message;
    }

    public User getUser() {
        return this.message.getAuthor();
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getArgs() {
        return this.args;
    }

}
