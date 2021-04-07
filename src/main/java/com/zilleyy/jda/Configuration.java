package com.zilleyy.jda;

import net.dv8tion.jda.api.entities.TextChannel;

import java.text.DecimalFormat;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 9:58 am AEST
 */
public class Configuration {

    public static final Configuration INSTANCE;

    private int ticketID = 0;

    static { INSTANCE = new Configuration(); }

    private Configuration() {}

    public String getPrefix() {
        return "/";
    }

    public String getNextTicketID() {
        this.ticketID++;
        return new DecimalFormat("0000").format(this.ticketID);
    }

    public TextChannel getConsoleChannel() {
        return Engine.getInstance().getJda().getTextChannelsByName("console", true).get(0);
    }

}
