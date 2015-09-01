package com.mobarenas.bungeecord.servercommunications;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by HP1 on 8/30/2015.
 */
public class ReceiveMessage {

    private BungeeHelper plugin;

    public ReceiveMessage() {
        plugin = BungeeHelper.getInstance();
    }

    public void receiveStatusRequest(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);

        String[] request = in.readUTF().split(":");
        ProxiedPlayer sender = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        String[] splitPlayerIDs = request[1].split(";");
        List<UUID> requestedPlayers = new ArrayList<>();
        for (String string : splitPlayerIDs) {
            requestedPlayers.add(UUID.fromString(string));
        }

        List<UUID> response = new ArrayList<>();
        for (UUID id : requestedPlayers) {
            if (BungeeHelper.getInstance().getProxy().getPlayer(id) != null)
                response.add(id);
        }
        BungeeHelper.getMessageSender().sendPlayerStatusResponse(sender, response);
    }

    public void receivePartyUpdate(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();

        BungeeHelper.getMessageSender().sendPartyUpdate(request);
    }

    public void receivePartyQuitUpdate(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();

        BungeeHelper.getMessageSender().sendPartyQuitUpdate(request);
    }

    public void receivePartyChatToggle(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);

        String[] request = in.readUTF().split(":");
        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        boolean value = Boolean.valueOf(request[1]);

        BungeeHelper.getPartyChat().setPartyChatValue(player, value);
    }

    public void receivePartyInvite(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);

        String[] request = in.readUTF().split(":");
        ProxiedPlayer sender = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        Collection<ProxiedPlayer> players = BungeeCord.getInstance().matchPlayer(request[1]);
        String partyID = request[2];

        ProxiedPlayer receiver = null;
        for (ProxiedPlayer p : players) {
            receiver = p;
            break;
        }
        if (receiver == null) {
            sender.sendMessage(new ComponentBuilder("Error: no players found with that name.").color(ChatColor.RED).create());
            return;
        }
        if (receiver.getUniqueId().equals(sender.getUniqueId())) {
            sender.sendMessage(new ComponentBuilder("Error: you cannot invite yourself to a party").color(ChatColor.RED).create());
            return;
        }
        sender.sendMessage(new ComponentBuilder("Successfully sent " + receiver.getName() + " an invite.").color(ChatColor.GREEN).create());
        receiver.sendMessage(new ComponentBuilder(sender.getName() + " has invited you to a party! Use /party join to accept.").color(ChatColor.GREEN).create());
        BungeeHelper.getMessageSender().sendPartyInvite(partyID, receiver);
    }

    public void receivePartyChatMessage(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();
        BungeeHelper.getMessageSender().sendPartyChatMessage(request);
    }

    public void receiveDeathAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String[] request = in.readUTF().split(":");

        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        int wave = Integer.parseInt(request[1]);


        BungeeHelper.getTitleManager().sendDeathTitle(wave, player);
    }

    public void receiveCamperKickAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        ProxiedPlayer player = BungeeHelper.getInstance().getProxy().getPlayer(UUID.fromString(in.readUTF()));

        if (player != null)
            BungeeHelper.getKickManager().addCamper(player);
    }

    public void receiveViolationAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String violator = in.readUTF();

        for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {
            if (player.hasPermission("bungeecord.violations.recieve")) {
                player.sendMessage(new ComponentBuilder("[WARNING] ").color(ChatColor.RED).append(violator).color(ChatColor.YELLOW)
                        .append(" has an unusually high violation level. /spectate " + violator + " to view them.").color(ChatColor.RED).create());
            }
        }
    }

    public void receivePurchaseAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        BungeeHelper.getTitleManager().handlePurchaseAlert(in.readUTF());
    }

    public void receiveArenaStartAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);

        String[] response = in.readUTF().split(":");
        String arenaName = response[0];
        String[] playerIDs = response[1].split(";");

        for (String id : playerIDs) {
            UUID uuid = UUID.fromString(id);
            ProxiedPlayer player = BungeeCord.getInstance().getPlayer(uuid);
            if (player != null)
                BungeeHelper.getTitleManager().handleStartAlert(arenaName, player);
        }
    }
}
