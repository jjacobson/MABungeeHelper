package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.UUID;

public class MessageReceiveListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) throws IOException {

        if (!(event.getSender() instanceof Server))
            return;

        if (event.getTag().equals("party-status-request")) {
            BungeeHelper.getMessageReceiver().receiveStatusRequest(event);
            return;
        }

        if (event.getTag().equals("party-join-update")) {
            BungeeHelper.getMessageReceiver().receivePartyUpdate(event);
            return;
        }

        if (event.getTag().equals("party-quit-update")) {
            BungeeHelper.getMessageReceiver().receivePartyQuitUpdate(event);
            return;
        }

        if (event.getTag().equals("party-chat-toggle")) {
            BungeeHelper.getMessageReceiver().receivePartyChatToggle(event);
            return;
        }

        if (event.getTag().equals("party-invite-player")) {
            BungeeHelper.getMessageReceiver().receivePartyInvite(event);
            return;
        }

        if (event.getTag().equals("party-chat-channel")) {
            BungeeHelper.getMessageReceiver().receivePartyChatMessage(event);
            return;
        }

        if (event.getTag().equals("death-alerts")) {
            BungeeHelper.getMessageReceiver().receiveDeathAlert(event);
            return;
        }

        if (event.getTag().equals("camp-kick-alerts")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            ProxiedPlayer player = BungeeHelper.getInstance().getProxy().getPlayer(in.readUTF());

            if (player != null)
                BungeeHelper.getInstance().addKickedPlayer(player);

            return;

        }

        if (event.getTag().equals("violation-alerts")) {

            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);
            String violater = in.readUTF();

            for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {
                if (player.hasPermission("bungeecord.violations.recieve"))
                    player.sendMessage(new ComponentBuilder("[WARNING] ").color(ChatColor.RED).append(violater).color(ChatColor.YELLOW)
                            .append(" has an unusually high violation level. /spectate " + violater + " to view them.").color(ChatColor.RED).create());
            }
            return;
        }

        if (event.getTag().equals("purchase-alerts")) {

            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            BungeeHelper.getInstance().getMessageHandler().handlePurchaseAlert(in.readUTF());
            return;
        }

        if (event.getTag().equals("arena-start-alerts")) {

            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            String[] response = in.readUTF().split(":");
            String arenaName = response[0];
            String[] playerIDs = response[1].split(";");

            for (String id : playerIDs) {
                UUID uuid = UUID.fromString(id);
                ProxiedPlayer player = BungeeCord.getInstance().getPlayer(uuid);
                if (player != null)
                    BungeeHelper.getInstance().getMessageHandler().handleStartAlert(arenaName, player);
            }
            return;
        }

    }

}
