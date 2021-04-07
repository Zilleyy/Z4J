package com.zilleyy.jda.console;

import com.zilleyy.jda.Configuration;
import com.zilleyy.jda.Engine;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 8:31 pm AEST
 */
public class ConsoleLogger implements Runnable {

    private Thread thread;
    private static ConsoleLogger instance;

    private static TextChannel channel = Configuration.INSTANCE.getConsoleChannel();
    private static AtomicBoolean sent = new AtomicBoolean(false), clear = new AtomicBoolean(false);

    public ConsoleLogger() {
        instance = this;
        this.clear();
    }

    /**
     * Clears all the messages in the console log
     * by duplicating the console channel and deleted the old one.
     */
    public static void clear() {
        TextChannel oldChannel = Configuration.INSTANCE.getConsoleChannel();
        ChannelAction newChannel = oldChannel.createCopy();
        oldChannel.delete().queue();
        channel = (TextChannel) newChannel.complete();
    }

    /**
     * Sends a message to the console log text channel.
     * @param message the message to send to the channel.
     * @return the ConsoleLogger instance.
     */
    public static void send(String message) {
        // TODO add sent checker to stop a message trying to send before the last message has successfully sent.
        OffsetDateTime time = OffsetDateTime.now();
        String formatted = time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy @ hh:mm:ss a"));
        Configuration.INSTANCE
                .getConsoleChannel()
                .sendMessage("``[" + formatted + "] " + message + "``")
                .queue(success -> sent.set(true), failure -> log(message));
        await();
    }

    public static void input(String message) {
        Configuration.INSTANCE
                .getConsoleChannel()
                .sendMessage("``> " + message + "``")
                .queue(success -> sent.set(true), failure -> log(message));
        await();
    }

    public static void print(String message) {
        Configuration.INSTANCE
                .getConsoleChannel()
                .sendMessage("``" + message + "``")
                .queue(success -> sent.set(true));
        await();
    }

    public static void execute(String message) {
        if(message.toLowerCase().startsWith("print ")) {
            print(message.replaceAll("print", ""));
        }
    }

    public static void error(Exception exception) {
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(0xf44444)
                .setTitle(exception.getClass().getName())
                .setDescription("**StackTrace:** ```" + Arrays.toString(exception.getStackTrace()).replaceAll(",", "") + "```")
                .setFooter("This event has been logged");
        channel.sendMessage(builder.build()).queue(success -> sent.set(true));
        await();
    }

    public static void embed(String[] lines) {

    }

    public static void log(String message) {
        OffsetDateTime time = OffsetDateTime.now();
        String formatted = time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy @ hh:mm:ss a"));
        System.out.println("[" + Thread.currentThread().getName() + "]" + " [" + formatted + "] " + message);
        sent.set(true);
    }

    /**
     * Freezes the thread until the message has been sent successfully.
     */
    public static void await() {
        // Loop until the message is sent
        while (!sent.get());
        // Assign the value false to sent to be ready for the next message
        sent.set(false);
    }

    public static ConsoleLogger getInstance() {
        return ConsoleLogger.instance;
    }

    @Override
    public void run() {
        send("JDA successfully logged in as " + Engine.getInstance().getJda().getSelfUser().getAsTag());
        await();
    }

    public void start() {
        if (this.thread == null) {
            this.thread = new Thread (this, "CONSOLE");
            this.thread.start();
        } else {
            this.send("Error: tried to start thread that has already been started.");
        }
    }

    static {
        new ConsoleLogger().start();
    }

    public Thread getThread() {
        return this.thread;
    }
}
