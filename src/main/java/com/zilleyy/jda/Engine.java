package com.zilleyy.jda;

import com.zilleyy.jda.console.ConsoleListener;
import com.zilleyy.jda.console.ConsoleLogger;
import com.zilleyy.jda.listener.GeneralListener;
import com.zilleyy.jda.listener.MessageListener;
import com.zilleyy.jda.listener.ReactionListener;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
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

    @Getter private JDA JDA;
    @Getter private Guild guild;

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
        configure(JDABuilder.createDefault("ODI0NDk5NTU2Njc3ODQ1MDMy.YFwRDA.ZmF7spOmAIZ_XPPXc63yyRPaMuA"));
        this.JDA.getPresence().setActivity(Activity.watching("messages in channels"));
        this.JDA.awaitReady();

        this.JDA.getGuilds().forEach(guild -> {
            Task<List<Member>> task = guild.loadMembers();
            task.onError(error -> error.printStackTrace());
            task.onSuccess(success -> log(success.toString()));
        });

        this.guild = this.JDA.getGuilds().get(0);

        new Constant();
        new BridgeClient();
    }

    public void stop() {
        this.JDA.shutdown();
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
        this.JDA = builder.build();
    }

    private ListenerAdapter[] instantiateListeners() throws IllegalAccessException, InstantiationException {
        Class<? extends ListenerAdapter>[] classes = new Class[] {
                ConsoleListener.class,
                MessageListener.class,
                ReactionListener.class,
                GeneralListener.class
        };

        ListenerAdapter[] instances = new ListenerAdapter[classes.length];

        for(int i = 0; i < classes.length; i++) {
            instances[i] = classes[i].newInstance();
        }

        return instances;
    }

}
