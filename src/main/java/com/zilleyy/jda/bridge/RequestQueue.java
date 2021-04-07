package com.zilleyy.jda.bridge;

import java.util.LinkedList;

/**
 * Author: Zilleyy
 * <br>
 * Date: 2/04/2021 @ 9:33 pm AEST
 */
public final class RequestQueue {

    private static final LinkedList<String> requests = new LinkedList<>();

    public static final void add(final String path) {
        RequestQueue.requests.add(path);
    }

    public static final void next() {
        if(RequestQueue.requests.size() <= 0) return;
        final String request = RequestQueue.requests.get(0);
        Rest.post(request);
        RequestQueue.requests.remove(0);
    }

}
