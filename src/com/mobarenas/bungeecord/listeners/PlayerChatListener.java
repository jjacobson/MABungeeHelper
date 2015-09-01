package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void playerChat(ChatEvent event) {

        if (!(event.getSender() instanceof ProxiedPlayer))
            return;

        if (event.isCommand())
            return;

        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

        for (ProxiedPlayer spy : BungeeHelper.getSpyManager().getSpies()) {
            if (sender == spy)
                continue;

            // Dont send to players on the same server
            if (sender.getServer().getInfo().getName().equals(spy.getServer().getInfo().getName()))
                continue;

            String server = (sender.getServer().getInfo().getName().startsWith("slave")) ? "GAME" : "LOBBY";

            spy.sendMessage(new ComponentBuilder("[" + server + "]").color(ChatColor.GOLD).append(sender.getName() + ": ").color(ChatColor.GRAY).append(event.getMessage()).color(ChatColor.GRAY)
                    .create());

        }

    }

}