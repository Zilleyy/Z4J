package com.zilleyy.jda.console;

import com.zilleyy.jda.Configuration;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static com.zilleyy.jda.console.ConsoleLogger.execute;
import static com.zilleyy.jda.console.ConsoleLogger.input;

/**
 * Author: Zilleyy
 * <br>
 * Date: 27/03/2021 @ 12:15 pm AEST
 */
public class ConsoleListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!event.getChannel().equals(Configuration.INSTANCE.getConsoleChannel())) return;
        if(event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentRaw();
        event.getMessage().delete().queue();
        input(message);
        execute(message);
    }

}
