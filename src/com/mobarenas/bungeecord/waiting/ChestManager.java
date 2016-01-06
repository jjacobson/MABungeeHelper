package com.mobarenas.bungeecord.waiting;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP1 on 1/5/2016.
 */
public class ChestManager {

    private List<ProxiedPlayer> received = new ArrayList<>();

    /**
     * Add a receiver to the list of receivers if he is not already in it
     *
     * @param player receiver
     */
    public void addReceiver(ProxiedPlayer player) {
        if (!received.contains(player))
            received.remove(player);
    }

    /**
     * Remove a receiver from the list of receivers
     *
     * @param player who is no longer a receiver
     */
    public void removeReceiver(ProxiedPlayer player) {
        received.remove(player);
    }

    /**
     * Check if a player is a receiver
     *
     * @param player to check receiver status
     * @return whether or not this player is a receiver
     */
    public boolean isReceiver(ProxiedPlayer player) {
        return received.contains(player);
    }

}
