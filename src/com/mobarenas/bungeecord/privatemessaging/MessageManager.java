package com.mobarenas.bungeecord.privatemessaging;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP1 on 8/30/2015.
 */
public class MessageManager {

    private Map<ProxiedPlayer, ProxiedPlayer> messengers = new HashMap<>();

    public Map<ProxiedPlayer, ProxiedPlayer> getMessengers() {
        return messengers;
    }

    public void removeMessenger(ProxiedPlayer player) {
        messengers.remove(player);
    }

    public void addMessenger(ProxiedPlayer sender, ProxiedPlayer receiver) {
        messengers.put(sender, receiver);
    }

    public boolean isMessenger(ProxiedPlayer player) {
        return messengers.containsKey(player);
    }
}
