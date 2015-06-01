package com.mobarenas.bungeecord.commands;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.utils.MessageCommandHelper;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCommand extends Command {

    public ReplyCommand() {
	super("reply", "bungeecord.command.reply", "r", "re");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

	if (!(commandSender instanceof ProxiedPlayer))
	    return;

	ProxiedPlayer player = (ProxiedPlayer) commandSender;

	if (args.length < 1) {
	    player.sendMessage(new ComponentBuilder("Error: correct usage /r <message>").color(ChatColor.RED).create());
	    return;
	}

	if (!BungeeHelper.getInstance().containsPlayer(player)) {
	    player.sendMessage(new ComponentBuilder("Error: no one has messaged you.").color(ChatColor.RED).create());
	    return;
	}

	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < args.length; i++) {
	    sb.append(args[i] + " ");
	}

	if (BungeeHelper.getInstance().getProxiedPlayerList().get(player) == null) {
	    player.sendMessage(new ComponentBuilder("Error: that player is not online.").color(ChatColor.RED).create());
	    return;
	}

	ProxiedPlayer reciever = BungeeHelper.getInstance().getProxiedPlayerList().get(player);

	MessageCommandHelper.sendPlayerMessage(player, reciever, sb.toString());
	MessageCommandHelper.setPlayerSenders(player, reciever);
    }

}
