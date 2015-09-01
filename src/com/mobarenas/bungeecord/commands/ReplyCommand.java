package com.mobarenas.bungeecord.commands;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.privatemessaging.MessageCommandUtils;
import net.md_5.bungee.api.CommandSender;
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
            player.sendMessage(Messages.getMessage("message.reply-args"));
            return;
        }

        if (!BungeeHelper.getMessageManager().isMessenger(player)) {
            player.sendMessage(Messages.getMessage("message.reply-null"));
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i] + " ");
        }

        ProxiedPlayer receiver = BungeeHelper.getMessageManager().getMessengers().get(player);
        if (receiver == null) {
            player.sendMessage(Messages.getMessage("message.reply-offline"));
            return;
        }
        MessageCommandUtils.sendPlayerMessage(player, receiver, sb.toString());
        MessageCommandUtils.setPlayerSenders(player, receiver);
    }

}
