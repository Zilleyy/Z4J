package com.zilleyy.jda;

import com.google.gson.Gson;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Role;

/**
 * Author: Zilleyy
 * <br>
 * Date: 14/05/2021 @ 10:36 am AEST
 */
public class Constant {

    @Getter private static Constant instance;

    public final String HOST = "ws://localhost:3000/";
    public final String WEB_SOCKET_CLOSED_UNEXPECTEDLY = "WebSocket connection closed unexpectedly, attempting to reconnect...";
    public final String WEB_SOCKET_CLOSED = "Connection to WebSocket closed: ";
    public final Gson GSON = new Gson();
    public final Role MUTE_ROLE;

    public Constant() {
        Constant.instance = this;

        this.MUTE_ROLE = Engine.getInstance().getGuild().getRoleById(839689210667466752L);
    }

    public static String getHost() {
        return Constant.getInstance().HOST;
    }

    public static String getWebSocketClosedUnexpectedly() {
        return Constant.getInstance().WEB_SOCKET_CLOSED_UNEXPECTEDLY;
    }

    public static String getWebSocketClosed() {
        return Constant.getInstance().WEB_SOCKET_CLOSED;
    }

    public static Gson getGson() {
        return Constant.getInstance().GSON;
    }

    public static Role getMuteRole() {
        return Constant.getInstance().MUTE_ROLE;
    }

}
