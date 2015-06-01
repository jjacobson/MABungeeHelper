package com.mobarenas.bungeecord.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.mobarenas.bungeecord.BungeeHelper;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageRecieve implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) throws IOException {

	if (!(event.getSender() instanceof Server))
	    return;

	if (event.getTag().equals("death-alerts")) {
	    ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
	    DataInputStream in = new DataInputStream(stream);

	    BungeeHelper.getInstance().getMessageHandler().handleDeathAlert(in.readUTF());
	    return;
	}

	if (event.getTag().equals("camp-kick-alerts")) {
	    ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
	    DataInputStream in = new DataInputStream(stream);

	    ProxiedPlayer player = BungeeHelper.getInstance().getProxy().getPlayer(in.readUTF());

	    if (player != null)
		BungeeHelper.getInstance().addKickedPlayer(player);

	    return;

	}

	if (event.getTag().equals("violation-alerts")) {

	    ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
	    DataInputStream in = new DataInputStream(stream);
	    String violater = in.readUTF();

	    for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {
		if (player.hasPermission("bungeecord.violations.recieve"))
		    player.sendMessage(new ComponentBuilder("[WARNING] ").color(ChatColor.RED).append(violater).color(ChatColor.YELLOW)
			    .append(" has an unusually high violation level. /spectate " + violater + " to view them.").color(ChatColor.RED).create());
	    }

	    return;
	}

    }

}
