package com.zilleyy.jda;

import com.zilleyy.jda.console.ConsoleLogger;
import jakarta.websocket.*;
import lombok.SneakyThrows;

import java.net.ConnectException;
import java.net.URI;

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

            this.waitForTerminationSignal();
        } finally {
            if(this.session != null) this.session.close();
        }
    }

    @SneakyThrows
    private boolean createSession() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, URI.create(Constant.HOST));
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
        System.out.println("Opening WebSocket");
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
        System.out.println("Closing WebSocket: " + reason);
        this.session = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @SneakyThrows
    private static void waitForTerminationSignal() {
        synchronized (waitLock) {
            waitLock.wait();
        }
    }

}
