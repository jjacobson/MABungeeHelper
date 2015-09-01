package com.mobarenas.bungeecord.privatemessaging;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageCommandUtils {

    public static void sendPlayerMessage(ProxiedPlayer sender, ProxiedPlayer receiver, String message) {
        sender.sendMessage(new ComponentBuilder("You ").color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append(receiver.getName()).color(ChatColor.WHITE).append(": ").color(ChatColor.GRAY)
                .append(ChatColor.stripColor(message)).create());

        receiver.sendMessage(new ComponentBuilder(sender.getName()).color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append("You").color(ChatColor.WHITE).append(": ").color(ChatColor.GRAY)
                .append(ChatColor.stripColor(message)).create());

        for (ProxiedPlayer player : BungeeHelper.getSpyManager().getSpies()) {
            if (player == sender || player == receiver)
                continue;

            player.sendMessage(new ComponentBuilder(sender.getName()).color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append(receiver.getName()).color(ChatColor.WHITE).append(": ")
                    .color(ChatColor.GRAY).append(ChatColor.stripColor(message)).create());
        }
    }

    public static void setPlayerSenders(ProxiedPlayer sender, ProxiedPlayer receiver) {
        BungeeHelper.getMessageManager().addMessenger(sender, receiver);
        BungeeHelper.getMessageManager().addMessenger(receiver, sender);
    }

}
