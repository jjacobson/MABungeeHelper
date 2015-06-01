package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;

import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitch implements Listener {
    
    @EventHandler
    public void serverSwitch(ServerSwitchEvent event) {
	
	if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase("Lobby")) {
	    return;
	}
	if (BungeeHelper.getInstance().hasBeenKicked(event.getPlayer())) {
	    BungeeHelper.getInstance().getMessageHandler().handleKickAlert(event.getPlayer());
	    BungeeHelper.getInstance().removeKickedPlayer(event.getPlayer());
	}

    }

}
