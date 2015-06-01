package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeJoin implements Listener {
    
    @EventHandler
    public void lobbyJoin(PostLoginEvent event) {
	BungeeHelper.getInstance().getMessageHandler().handleLoginAlert(event.getPlayer());
    }

}
