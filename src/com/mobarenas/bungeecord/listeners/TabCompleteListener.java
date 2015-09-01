package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteListener implements Listener {

    private List<String> autoComplete = new ArrayList<String>() {{
        add("join");
        add("spec");
        add("spectate");
    }};

    @EventHandler
    public void tabComplete(TabCompleteEvent event) {

        if (!(event.getSender() instanceof ProxiedPlayer))
            return;

        String[] args = event.getCursor().split(" ");
        String cmd = args[0].replaceFirst("/", "");

        if (autoComplete.contains(cmd.toLowerCase())) {
            if (args.length < 2)
                return;

            event.getSuggestions().addAll(getSuggestions(args[1], (ProxiedPlayer) event.getSender()));
            return;
        }
    }

    /**
     * Add tablist suggestions to everyone who isnt on your server
     *
     * @param arg    command argument (playername)
     * @param sender sender
     * @return list of players to add to the tab suggestions
     */
    private List<String> getSuggestions(String arg, ProxiedPlayer sender) {
        List<String> players = new ArrayList<>();
        for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().matchPlayer(arg)) {
            if (player.getServer() == sender.getServer())
                continue; // bukkit already adds them to the list

            players.add(player.getName());
        }
        return players;
    }

}
