package com.zilleyy.jda.bridge;

import lombok.SneakyThrows;

/**
 * Author: Zilleyy
 * <br>
 * Date: 2/04/2021 @ 9:53 pm AEST
 */
public class RequestHandler extends Thread {

    public RequestHandler() {
        super();
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            Thread.sleep(100);
            RequestQueue.next();
        }
    }

}
