package com.mobarenas.bungeecord.listeners;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.mobarenas.bungeecord.BungeeHelper;

public class TabComplete implements Listener {

    @EventHandler
    public void tabComplete(TabCompleteEvent event) {

	if (!(event.getSender() instanceof ProxiedPlayer))
	    return;

	String[] args = event.getCursor().split(" ");
	String cmd = args[0].replaceFirst("/", "");

	if (cmd.equalsIgnoreCase("join")) {
	    if (args.length < 2)
		return;

	    event.getSuggestions().addAll(getSuggestions(args[1], (ProxiedPlayer) event.getSender()));

	    return;
	}

	if (cmd.equalsIgnoreCase("spectate")) {
	    if (args.length < 2)
		return;

	    event.getSuggestions().addAll(getSuggestions(args[1], (ProxiedPlayer) event.getSender()));

	    return;
	}

    }

    /**
     * Add tablist suggestions to everyone who isnt on your server
     * 
     * @param arg
     *            command argument (playername)
     * @param tabber
     *            sender
     * @return list of players to add to the tab suggestions
     */
    private List<String> getSuggestions(String arg, ProxiedPlayer tabber) {

	List<String> players = new ArrayList<String>();

	for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {

	    if (player.getServer() == tabber.getServer())
		continue;

	    if (player.getName().startsWith(arg)) {
		players.add(player.getName());
	    }

	}
	return players;
    }

}
