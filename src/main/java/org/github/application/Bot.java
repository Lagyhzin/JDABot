package org.github.application;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.github.application.command.BasicCommands;
import org.github.application.command.CommandManager;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bot {
    public static void main(String[] args) {
        try {
            Dotenv env = Dotenv.configure().load();
            String token = env.get("TOKEN");

            if (token == null) {
                System.out.println("ERRO: Token não encontrado no arquivo .env.");
                return;
            }

            JDA jda = JDABuilder.createDefault(token)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .build();

            jda.addEventListener(new CommandManager());
            jda.addEventListener(new BasicCommands());

            jda.awaitReady();


            System.out.println(" ________  ________  _________        ________  ________  ________  ________   _________  ________                                                    \n" +
                    "|\\   __  \\|\\   __  \\|\\___   ___\\     |\\   __  \\|\\   __  \\|\\   __  \\|\\   ___  \\|\\___   ___\\\\   __  \\                                                   \n" +
                    "\\ \\  \\|\\ /\\ \\  \\|\\  \\|___ \\  \\_|     \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\\\ \\  \\|___ \\  \\_\\ \\  \\|\\  \\                                                  \n" +
                    " \\ \\   __  \\ \\  \\\\\\  \\   \\ \\  \\       \\ \\   ____\\ \\   _  _\\ \\  \\\\\\  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\                                                 \n" +
                    "  \\ \\  \\|\\  \\ \\  \\\\\\  \\   \\ \\  \\       \\ \\  \\___|\\ \\  \\\\  \\\\ \\  \\\\\\  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\                                                \n" +
                    "   \\ \\_______\\ \\_______\\   \\ \\__\\       \\ \\__\\    \\ \\__\\\\ _\\\\ \\_______\\ \\__\\\\ \\__\\   \\ \\__\\ \\ \\_______\\                                               \n" +
                    "    \\|_______|\\|_______|    \\|__|        \\|__|     \\|__|\\|__|\\|_______|\\|__| \\|__|    \\|__|  \\|_______|                 ");



            List<Activity> activities = Arrays.asList(
                    Activity.listening("Spotify"),
                    Activity.playing("Minecraft"),
                    Activity.watching("Youtube"),
                    Activity.playing("Valorant"),
                    Activity.listening("Tyler The Creator")
            );

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
               int randomIndex = (int) (Math.random() * activities.size());
               Activity activity = activities.get(randomIndex);
               jda.getPresence().setActivity(activity);
            },0, 10, TimeUnit.SECONDS);
        }  catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}