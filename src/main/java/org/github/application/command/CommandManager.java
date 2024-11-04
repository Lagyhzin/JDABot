package org.github.application.command;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash("serverinfo", "Mostra informações sobre o servidor."));
        commands.add(Commands.slash("ping", "Mostra o ping do bot."));
        commands.add(Commands.slash("mcavatar", "Veja o avatar de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("mcbody", "Veja o body de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("mcskin", "Veja a skin de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("say", "Use o bot para falar algo.")
                .addOption(OptionType.STRING, "mensagem", "A mensagem que o bot replicará.", true));

        event.getGuild().updateCommands().addCommands(commands).queue();

    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash("serverinfo", "Mostra informações sobre o servidor."));
        commands.add(Commands.slash("ping", "Mostra o ping do bot."));
        commands.add(Commands.slash("mcavatar", "Veja o avatar de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("mcbody", "Veja o body de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("mcskin", "Veja a skin de um jogador que deseja.")
                .addOption(OptionType.STRING, "nick", "O nick do jogador.", true));
        commands.add(Commands.slash("say", "Use o bot para falar algo.")
                .addOption(OptionType.STRING, "mensagem", "A mensagem que o bot replicará.", true));

        event.getGuild().updateCommands().addCommands(commands).queue();

    }
}
