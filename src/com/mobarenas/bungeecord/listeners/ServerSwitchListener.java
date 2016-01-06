package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {

    @EventHandler
    public void serverSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo serverInfo = player.getServer().getInfo();

        if (BungeeHelper.getPartyChat().isInPartyChat(player)) {
            BungeeHelper.getMessageSender().sendPartyChatStatus(player, serverInfo);
        }

        if (serverInfo.getName().equalsIgnoreCase("lobby") && BungeeHelper.getKickManager().isCamper(player)) {
            BungeeHelper.getTitleManager().handleKickAlert(player);
            BungeeHelper.getKickManager().removeCamper(player);
        }
        if (serverInfo.getName().equalsIgnoreCase("lobby") && BungeeHelper.getChestManager().isReceiver(player)) {
            player.sendMessage(Messages.getMessage("chest"));
            BungeeHelper.getChestManager().removeReceiver(player);
        }
    }

}
