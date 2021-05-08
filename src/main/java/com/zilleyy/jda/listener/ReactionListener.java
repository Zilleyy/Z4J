package com.zilleyy.jda.listener;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Author: Zilleyy
 * <br>
 * Date: 6/05/2021 @ 1:24 pm AEST
 */
public class ReactionListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getMessageIdLong() != 839703076029595699L) return;
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Verified", false).get(0)).queue();
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRolesByName("Unverified", false).get(0)).queue();
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if(event.getMessageIdLong() != 839703076029595699L) return;
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRolesByName("Verified", false).get(0)).queue();
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Unverified", false).get(0)).queue();
    }

}
