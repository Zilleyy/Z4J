package com.zilleyy.jda.data;

import com.zilleyy.jda.Constant;
import com.zilleyy.jda.Engine;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Zilleyy
 * <br>
 * Date: 16/05/2021 @ 10:31 am AEST
 */
@AllArgsConstructor
public class Mute {

    @Getter private final String userId;
    @Getter private final long duration = 0;

    public boolean fulfill() {
        Engine.getInstance().getGuild().addRoleToMember(this.userId, Constant.getMuteRole()).queue();
        return true;
    }

}
