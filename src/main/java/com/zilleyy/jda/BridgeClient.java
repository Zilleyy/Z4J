package com.zilleyy.jda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zilleyy.jda.console.ConsoleLogger;
import com.zilleyy.jda.data.Mute;
import jakarta.websocket.*;
import lombok.SneakyThrows;

import java.net.ConnectException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Author: Zilleyy
 * <br>
 * Date: 14/05/2021 @ 10:27 am AEST
 */
@ClientEndpoint
public class BridgeClient {

    private static final Object waitLock = new Object();

    private Session session;

    @SneakyThrows
    public BridgeClient() {
        try {
            if(this.createSession()) ConsoleLogger.raw("Successfully created WebSocket session.");
            else ConsoleLogger.raw("Failed to create WebSocket session.");

            BridgeClient.waitForTerminationSignal();
        } finally {
            if(this.session != null) this.session.close();
        }
    }

    @SneakyThrows
    private boolean createSession() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, URI.create(Constant.getHost()));
            this.session.getAsyncRemote().sendText("{ \"bot\": \"true\" }");
            return true;
        } catch (DeploymentException | ConnectException exception) {
            return false;
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param session the session which is opened.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Opening WebSocket session...");
        this.session = session;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param session the session which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(Constant.getWebSocketClosed() + reason);
        this.session = null; // Assign the session to null as the client is no longer connected to the server.

        while(!this.createSession()); // Attempts to reconnect to the server.
    }

    @OnMessage
    public void onMessage(String message) throws ExecutionException, InterruptedException {
        if(!JSONUtils.isJSONValid(message)) return;
        JsonObject object = Constant.getGson().fromJson(message, JsonObject.class);
        Optional<Map.Entry<String, JsonElement>> entry = object.entrySet().stream().findFirst();
        if(object.size() > 0) assert object.entrySet().stream().findFirst().isPresent();

        switch(object.entrySet().stream().findFirst().get().getKey()) {
            case "mute":
                System.out.println("MUTE");
                Mute mute = Constant.getGson().fromJson(object, Mute.class);
                mute.fulfill();
                break;
        }
    }

    @SneakyThrows
    private static void waitForTerminationSignal() {
        synchronized (waitLock) {
            waitLock.wait();
        }
    }

}
