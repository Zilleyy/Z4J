package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.CommandScope;
import com.zilleyy.jda.command.ExecutionStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

/**
 * Author: Zilleyy
 * <br>
 * Date: 6/05/2021 @ 11:42 am AEST
 */
public class MuteCommand extends Command {

    public MuteCommand() {
        super("Mutes the specified user", CommandScope.GUILD, "mute");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        if(!information.hasArguments()) return ExecutionStatus.INCORRECT_ARGUMENTS;
        Member member = information.getGuild().getMemberByTag(information.getArgs()[0]);
        if(member == null) return ExecutionStatus.INVALID_USER;
        information.getGuild().addRoleToMember(member, this.getMuteRole(information.getGuild())).queue();
        information.getGuild().removeRoleFromMember(member, information.getGuild().getRolesByName("Verified", false).get(0)).queue();

        information.getMessage().reply("Muted user: " + member.getAsMention()).queue();
        return ExecutionStatus.GENERIC_SUCCESS;
    }

    /**
     * If it exists already, then find it and return it, otherwise create it and return it.
     * @return the mute role.
     */
    private Role getMuteRole(Guild guild) {
        List<Role> roles = guild.getRolesByName("Muted", false);

        if(roles.size() > 0) {
            return roles.get(0);
        } else {
            return null;
        }
    }

}
