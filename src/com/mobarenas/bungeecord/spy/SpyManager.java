package com.mobarenas.bungeecord.spy;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP1 on 8/30/2015.
 */
public class SpyManager {

    private List<ProxiedPlayer> spies = new ArrayList<>();

    /**
     * Add a new spy to the list of spies
     *
     * @param player to add as a spy
     */
    public void addSpy(ProxiedPlayer player) {
        if (!spies.contains(player))
            spies.add(player);
    }

    /**
     * Remove a spy from the spy list
     *
     * @param player who is no longer a spy
     */
    public void removeSpy(ProxiedPlayer player) {
        spies.remove(player);
    }

    /**
     * Check if a player is a spy
     *
     * @param player who we are checking spy status
     * @return whether or not this player is a spy
     */
    public boolean isSpy(ProxiedPlayer player) {
        return spies.contains(player);
    }

    /**
     * Get the entire list of current spies
     *
     * @return list of spies
     */
    public List<ProxiedPlayer> getSpies() {
        return spies;
    }
}
