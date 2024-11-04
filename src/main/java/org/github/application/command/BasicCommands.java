package org.github.application.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BasicCommands extends ListenerAdapter {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("serverinfo")) {
            User author = event.getUser();
            if (!author.isBot()) {
                User owner = event.getGuild().getOwner().getUser();
                Guild guild = event.getGuild();
                OffsetDateTime creationTime = guild.getTimeCreated();

                String tooltipDate = "<t:" + creationTime.toEpochSecond() + ":F>";

                long years = java.time.Duration.between(creationTime, OffsetDateTime.now()).toDays() / 365;

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("📡 " + guild.getName());
                embed.setThumbnail(guild.getIconUrl());
                embed.setColor(Color.decode("#ff9b00"));
                embed.addField("💻 ID", guild.getId(), true)
                        .addField("👑 Dono", "`" + owner.getAsTag() + "`\n" + "(" + owner.getId() + ")", true)
                        .addField("💬 Canais: (" + guild.getChannels().size() + ")", "📝 **Texto:** " + guild.getTextChannels().size() + "\n**🗣 Voz:** " + guild.getVoiceChannels().size(), true)
                        .addField("📅 Criado em", tooltipDate + " (" + years + " ano" + (years != 1 ? "s" : "") + ")", true)
                        .addField("👥 Membros (" + guild.getMemberCount() + ")", "", true);

                event.reply(author.getAsMention()).setEmbeds(embed.build()).queue();
            }
        }

        if (command.equalsIgnoreCase("ping")) {
            User author = event.getUser();
            if (!author.isBot()) {
                long ping = event.getJDA().getGatewayPing();
                event.reply("🏓 Pong! " + ping + "ms.").queue();
            }
        }

        if (command.equalsIgnoreCase("mcavatar")) {
            User author = event.getUser();
            if (!author.isBot()) {
                String nick = event.getOption("nick").getAsString();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Avatar de " + nick);
                embed.setColor(Color.decode("#ff9b00"));
                embed.setImage("https://mc-heads.net/avatar/" + nick);
                event.replyEmbeds(embed.build()).queue();
            }
        }
        if (command.equalsIgnoreCase("mcbody")) {
            User author = event.getUser();
            if (!author.isBot()) {
                String nick = event.getOption("nick").getAsString();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Body de " + nick);
                embed.setColor(Color.decode("#ff9b00"));
                embed.setImage("https://mc-heads.net/body/" + nick);
                event.replyEmbeds(embed.build()).queue();
            }
        }

        if (command.equalsIgnoreCase("mcskin")) {
            User author = event.getUser();
            if (!author.isBot()) {
                String nick = event.getOption("nick").getAsString();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Skin de " + nick);
                embed.setColor(Color.decode("#ff9b00"));
                embed.setImage("https://mc-heads.net/skin/" + nick);
                event.replyEmbeds(embed.build()).queue();
            }
        }


        if (command.equalsIgnoreCase("say")) {
            Member member = event.getMember();
            User author = event.getUser();

            if (!author.isBot()) {
                String message = event.getOption("mensagem").getAsString();
                if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                    event.reply("Mensagem enviada.").setEphemeral(true).queue(msg -> {
                        scheduler.schedule(() -> msg.deleteOriginal().queue(), 2, TimeUnit.SECONDS);
                    });
                    event.getChannel().sendMessage(message + "\n\n_Esta mensagem foi enviada por " + author.getAsMention() + "_").queue();
                } else {
                    event.reply("Você não possui permissão pra utilizar este comando.").setEphemeral(true).queue();
                }
            }
        }
    }
}
