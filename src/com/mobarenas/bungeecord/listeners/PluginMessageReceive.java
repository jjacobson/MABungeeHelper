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
import java.util.*;

public class PluginMessageReceive implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) throws IOException {

        if (!(event.getSender() instanceof Server))
            return;

        if (event.getData().equals("party-status-request")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            String[] initialResponse = in.readUTF().split(":");
            UUID sender = UUID.fromString(initialResponse[0]);
            String[] request = initialResponse[1].split(";");
            List<UUID> playerRequests = new ArrayList<>();

            for (String string : request) {
                playerRequests.add(UUID.fromString(string));
            }

            List<UUID> response = BungeeHelper.getInstance().getMessageHandler().handlePartyStatusRequest(playerRequests);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(responseStream);
            StringJoiner joiner = new StringJoiner(";");
            for (UUID id : response) {
                joiner.add(id.toString());
            }

            out.writeUTF(joiner.toString());

            BungeeHelper.getInstance().getProxy().getPlayer(sender).getServer().sendData("party-status-request", responseStream.toByteArray());
            return;
        }

        if (event.getTag().equals("party-invite-player")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            String[] request = in.readUTF().split(":");
            ProxiedPlayer sender = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
            String partyID = request[2];

            Collection<ProxiedPlayer> players = BungeeCord.getInstance().matchPlayer(request[1]);

            if (players.size() == 0) {
                sender.sendMessage(new ComponentBuilder("Error: no players found with that name.").color(ChatColor.RED).create());
            } else {
                ProxiedPlayer receiver = null;
                for (ProxiedPlayer p : players) {
                    receiver = p;
                    break;
                }
                sender.sendMessage(new ComponentBuilder("Successfully sent " + receiver.getName() + " an invite.").color(ChatColor.GREEN).create());
                receiver.sendMessage(new ComponentBuilder(sender.getName() + " has invited you to a party! Use /party join to accept.").color(ChatColor.GREEN).create());

                ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(responseStream);

                out.writeUTF(partyID);
                receiver.sendData("party-invite-player", responseStream.toByteArray());
            }
            return;
        }

        if (event.getTag().equals("party-chat-channel")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);
            String request = in.readUTF();

            UUID senderID = UUID.fromString(request.substring(0, 36));

            if (BungeeCord.getInstance().getPlayer(senderID) == null) {
                return;
            }

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(responseStream);

            out.writeUTF(request);

            for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
                // send once to each server
                for (ProxiedPlayer player : info.getPlayers()) {
                    player.sendData("party-chat-channel", responseStream.toByteArray());
                    break;
                }
            }
            return;
        }

        if (event.getTag().equals("death-alerts")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream in = new DataInputStream(stream);

            BungeeHelper.getInstance().getMessageHandler().handleDeathAlert(in.readUTF());
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
