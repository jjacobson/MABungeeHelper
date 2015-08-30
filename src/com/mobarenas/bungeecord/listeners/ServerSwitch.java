package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSwitch implements Listener {

    @EventHandler
    public void serverSwitch(ServerSwitchEvent event) {
        if (BungeeHelper.getInstance().getChatValue(event.getPlayer()) == true) {
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(responseStream);
            try {
                out.writeUTF(event.getPlayer().getUniqueId().toString() + ":true");
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getPlayer().getServer().getInfo().sendData("party-chat-toggle", responseStream.toByteArray());
        }

        if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase("Lobby")) {
            return;
        }
        if (BungeeHelper.getInstance().hasBeenKicked(event.getPlayer())) {
            BungeeHelper.getInstance().getMessageHandler().handleKickAlert(event.getPlayer());
            BungeeHelper.getInstance().removeKickedPlayer(event.getPlayer());
        }

    }

}
