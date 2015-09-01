package com.mobarenas.bungeecord.commands;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.messages.Pair;
import com.mobarenas.bungeecord.privatemessaging.MessageCommandUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class MessageCommand extends Command implements TabExecutor {

    public MessageCommand() {
        super("message", "bungeecord.command.message", "m", "msg", "tell");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if (!(commandSender instanceof ProxiedPlayer))
            return;

        ProxiedPlayer playerSender = (ProxiedPlayer) commandSender;

        if (args.length < 2) {
            playerSender.sendMessage(Messages.getMessage("message.message-args"));
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i] + " ");
        }

        for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {
            if (player.getName().equalsIgnoreCase(args[0])) {
                MessageCommandUtils.sendPlayerMessage(playerSender, player, sb.toString());
                MessageCommandUtils.setPlayerSenders(playerSender, player);
                return;
            }
        }
        playerSender.sendMessage(Messages.getMessage("message.not-online", new Pair("%player%", args[0])));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> players = new ArrayList<>();

        if (!(sender instanceof ProxiedPlayer)) {
            return players;
        }

        if (!(args.length > 0)) {
            return players;
        }

        for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().matchPlayer(args[0])) {
            players.add(player.getName());
        }
        return players;
    }

}
