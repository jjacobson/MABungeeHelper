package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void playerDisconnect(PlayerDisconnectEvent event) {
        removePlayerFootprint(event.getPlayer());
    }

    /**
     * Remove a players footprint, remove all references to the player
     *
     * @param player to clear
     */
    private void removePlayerFootprint(ProxiedPlayer player) {
        BungeeHelper.getMessageManager().removeMessenger(player);
        BungeeHelper.getSpyManager().removeSpy(player);
        BungeeHelper.getPartyChat().removePartyChatPlayer(player);
        BungeeHelper.getPartyChat().removePartyUUID(player);
        BungeeHelper.getKickManager().removeCamper(player);
        BungeeHelper.getChestManager().removeReceiver(player);
    }

}
