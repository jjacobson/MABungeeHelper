package com.mobarenas.bungeecord.privatemessaging;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP1 on 8/30/2015.
 */
public class MessageManager {

    private Map<ProxiedPlayer, ProxiedPlayer> messengers = new HashMap<>();

    /**
     * Get the list of messengers
     *
     * @return the map of messengers and their last messaged player
     */
    public Map<ProxiedPlayer, ProxiedPlayer> getMessengers() {
        return messengers;
    }

    /**
     * Remove a messenger from the map
     *
     * @param player messenger
     */
    public void removeMessenger(ProxiedPlayer player) {
        messengers.remove(player);
    }

    /**
     * Add a messenger and the receiver to the map
     *
     * @param sender   message sender
     * @param receiver message receiver
     */
    public void addMessenger(ProxiedPlayer sender, ProxiedPlayer receiver) {
        messengers.put(sender, receiver);
    }

    /**
     * Check if a player is a messenger with someone to reply to
     *
     * @param player to check messenger status for
     * @return whether or not the player is a messenger
     */
    public boolean isMessenger(ProxiedPlayer player) {
        return messengers.containsKey(player);
    }
}
