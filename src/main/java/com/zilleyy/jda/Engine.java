package com.zilleyy.jda;

import com.zilleyy.jda.bridge.RequestHandler;
import com.zilleyy.jda.console.ConsoleListener;
import com.zilleyy.jda.console.ConsoleLogger;
import com.zilleyy.jda.listener.MessageListener;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.concurrent.Task;

import java.util.List;

import static com.zilleyy.jda.console.ConsoleLogger.log;

/**
 * Author: Zilleyy
 * <br>
 * Date: 25/03/2021 @ 3:02 pm AEST
 */
public class Engine {

    @Getter private static Engine instance;

    @Getter private JDA jda;

    private Engine() {
        instance = this;
        this.init();
    }

    public static void main(String[] args) {
        new Engine();
    }

    public static Engine getInstance() {
        return Engine.instance;
    }

    @SneakyThrows
    private void init() {
        new RequestHandler().start();

        configure(JDABuilder.createDefault("ODI0NDk5NTU2Njc3ODQ1MDMy.YFwRDA.9sgANxN9tXVlyIn9_sX9TPPuIXc"));
        this.jda.getPresence().setActivity(Activity.watching("Messages in channels"));
        this.jda.awaitReady();

        this.jda.getGuilds().forEach(guild -> {
            Task<List<Member>> task = guild.loadMembers();
            task.onError(error -> error.printStackTrace());
            task.onSuccess(success -> log(success.toString()));
        });
    }

    public void stop() {
        this.jda.shutdown();
        ConsoleLogger.send("Shutting down");
        ConsoleLogger.await();
        //System.exit(0);
    }

    @SneakyThrows
    public void restart() {

    }

    @SneakyThrows
    private void configure(JDABuilder builder) {
        builder.disableCache(CacheFlag.ACTIVITY)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(this.instantiateListeners());
        this.jda = builder.build();
    }

    private ListenerAdapter[] instantiateListeners() throws IllegalAccessException, InstantiationException {
        Class<? extends ListenerAdapter>[] classes = new Class[] {
                ConsoleListener.class,
                MessageListener.class
        };

        ListenerAdapter[] instances = new ListenerAdapter[classes.length];

        for(int i = 0; i < classes.length; i++) {
            instances[i] = classes[i].newInstance();
        }

        return instances;
    }

}
