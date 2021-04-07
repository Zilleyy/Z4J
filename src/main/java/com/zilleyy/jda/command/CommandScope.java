package com.zilleyy.jda.command;

import net.dv8tion.jda.api.entities.Message;

/**
 * Author: Zilleyy
 * <br>
 * Date: 2/04/2021 @ 4:42 pm AEST
 */
public enum CommandScope {

    DM(false),
    GUILD(true),
    BOTH(true);

    private boolean flag;

    CommandScope(boolean flag) {
        this.flag = flag;
    }

    public boolean matches(Message message) {
        return (this.flag && message.getChannelType().isGuild()) || (!this.flag && !message.getChannelType().isGuild());
    }

}
