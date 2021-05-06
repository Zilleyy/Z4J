package com.zilleyy.jda.task;

import com.zilleyy.jda.bridge.Rest;
import com.zilleyy.jda.console.ConsoleLogger;
import org.json.JSONArray;

/**
 * Author: Zilleyy
 * <br>
 * Date: 11/04/2021 @ 12:41 pm AEST
 */
public class MessageSenderTask extends Thread {

    public MessageSenderTask() {
        super();
    }

    @Override
    public void run() {
        while(true) {
            String request = Rest.get("send");
            JSONArray array = new JSONArray(request);
            array.forEach(value -> {
                ConsoleLogger.print(value.toString());
            });
        }
    }

}
