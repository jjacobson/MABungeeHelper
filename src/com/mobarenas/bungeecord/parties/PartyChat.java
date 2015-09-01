package com.mobarenas.bungeecord.parties;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP1 on 8/30/2015.
 */
public class PartyChat {

    private Map<ProxiedPlayer, Boolean> partyChat = new HashMap<>();

    public boolean isInPartyChat(ProxiedPlayer player) {
        if (!partyChat.containsKey(player))
            return false;

        return partyChat.get(player);
    }

    public void setPartyChatValue(ProxiedPlayer player, boolean partyChatValue) {
        partyChat.put(player, partyChatValue);
    }

    public void removePartyChatPlayer(ProxiedPlayer player) {
        partyChat.remove(player);
    }
}
