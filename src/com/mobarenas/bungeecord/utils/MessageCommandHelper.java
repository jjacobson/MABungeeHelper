package com.mobarenas.bungeecord.utils;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageCommandHelper {

    public static void sendPlayerMessage(ProxiedPlayer sender, ProxiedPlayer reciever, String message) {
        sender.sendMessage(new ComponentBuilder("You ").color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append(reciever.getName()).color(ChatColor.WHITE).append(": ").color(ChatColor.GRAY)
                .append(ChatColor.stripColor(message)).create());

        reciever.sendMessage(new ComponentBuilder(sender.getName()).color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append("You").color(ChatColor.WHITE).append(": ").color(ChatColor.GRAY)
                .append(ChatColor.stripColor(message)).create());

        for (ProxiedPlayer player : BungeeHelper.getInstance().getSpies()) {

            if (player == sender || player == reciever)
                continue;

            player.sendMessage(new ComponentBuilder(sender.getName()).color(ChatColor.WHITE).append("-> ").color(ChatColor.GRAY).append(reciever.getName()).color(ChatColor.WHITE).append(": ")
                    .color(ChatColor.GRAY).append(ChatColor.stripColor(message)).create());
        }

    }

    public static void setPlayerSenders(ProxiedPlayer sender, ProxiedPlayer reciever) {
        BungeeHelper.getInstance().addPlayer(sender, reciever);
        BungeeHelper.getInstance().addPlayer(reciever, sender);
    }

}
