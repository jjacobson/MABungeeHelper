package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeLeave implements Listener {

    @EventHandler
    public void playerDisconnect(PlayerDisconnectEvent event) {

        BungeeHelper.getInstance().removePlayer(event.getPlayer());

        // Player quit and still exists in list
        // remove them so that it doesnt randomly pop up later
        if (BungeeHelper.getInstance().hasBeenKicked(event.getPlayer())) {
            BungeeHelper.getInstance().removeKickedPlayer(event.getPlayer());
        }
        BungeeHelper.getInstance().removeChatValue(event.getPlayer());
    }

}
