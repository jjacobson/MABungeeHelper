package com.mobarenas.bungeecord.campmanager;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP1 on 8/30/2015.
 */
public class KickManager {

    private List<ProxiedPlayer> campers = new ArrayList<>();

    /**
     * Add a camper to the list of campers if he is not already in it
     *
     * @param player camper
     */
    public void addCamper(ProxiedPlayer player) {
        if (!campers.contains(player))
            campers.remove(player);
    }

    /**
     * Remove a camper from the list of campers
     *
     * @param player who is no longer a camper
     */
    public void removeCamper(ProxiedPlayer player) {
        campers.remove(player);
    }

    /**
     * Check if a player is a camper
     *
     * @param player to check camper status
     * @return whether or not this player is a camper
     */
    public boolean isCamper(ProxiedPlayer player) {
        return campers.contains(player);
    }
}
