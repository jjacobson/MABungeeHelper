package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {

    @EventHandler
    public void serverSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo serverInfo = event.getPlayer().getServer().getInfo();

        if (BungeeHelper.getPartyChat().isInPartyChat(player)) {
            BungeeHelper.getMessageSender().sendPartyChatStatus(player, serverInfo);
        }

        if (serverInfo.getName().equalsIgnoreCase("lobby") && BungeeHelper.getKickManager().isCamper(player)) {
            BungeeHelper.getTitleManager().handleKickAlert(event.getPlayer());
            BungeeHelper.getKickManager().removeCamper(event.getPlayer());
        }
    }

}
