package com.mobarenas.bungeecord.parties;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by HP1 on 8/30/2015.
 */
public class PartyChat {

    private Map<ProxiedPlayer, Boolean> partyChat = new HashMap<>();
    private Map<ProxiedPlayer, UUID> partyIDs = new HashMap<>();

    /**
     * Check if a player is in the party chat
     *
     * @param player to check for
     * @return if the player is currently in party chat
     */
    public boolean isInPartyChat(ProxiedPlayer player) {
        if (!partyChat.containsKey(player))
            return false;

        return partyChat.get(player);
    }

    /**
     * Set whether or not the player is in party chat
     *
     * @param player         to change party chat value for
     * @param partyChatValue whether or not the player is now in party chat
     */
    public void setPartyChatValue(ProxiedPlayer player, boolean partyChatValue) {
        partyChat.put(player, partyChatValue);
    }

    /**
     * Set the UUID for the party the player is in
     *
     * @param player  who is in a party
     * @param partyID the UUID for the players current party
     */
    public void setPartyChatID(ProxiedPlayer player, UUID partyID) {
        partyIDs.put(player, partyID);
    }

    /**
     * Get the UUID for the party the player is in
     *
     * @param player who is in a party
     * @return the UUID for the players party
     */
    public UUID getPartyChatID(ProxiedPlayer player) {
        return partyIDs.get(player);
    }

    /**
     * Remove a player from the party chat map
     *
     * @param player to remove from the map
     */
    public void removePartyChatPlayer(ProxiedPlayer player) {
        partyChat.remove(player);
    }

    /**
     * Remove a player from the player partyid map
     *
     * @param player to remove
     */
    public void removePartyUUID(ProxiedPlayer player) {
        partyIDs.remove(player);
    }
}
