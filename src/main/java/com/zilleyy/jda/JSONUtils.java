package com.zilleyy.jda;

/**
 * Author: Zilleyy
 * <br>
 * Date: 16/05/2021 @ 4:37 pm AEST
 */
public final class JSONUtils {

    private JSONUtils(){}

    public static boolean isJSONValid(String jsonInString) {
        try {
            Constant.getGson().fromJson(jsonInString, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }
}
