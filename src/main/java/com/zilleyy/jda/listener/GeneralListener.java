package com.zilleyy.jda.listener;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Author: Zilleyy
 * <br>
 * Date: 6/05/2021 @ 1:39 pm AEST
 */
public class GeneralListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Unverified", false).get(0)).queue();
    }

}
