package com.mobarenas.bungeecord.servercommunications;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.messages.Pair;
import net.md_5.bungee.BungeeCord;
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

    /**
     * Receive the request for players bungee online status
     *
     * @param event PluginMessageEvent
     * @throws IOException
     */
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

    /**
     * Receive a party join update when a player joins a party
     * Allowing us to update all parties on all servers
     *
     * @param event PluginMessageEvent
     * @throws IOException
     */
    public void receivePartyJoinUpdate(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();

        BungeeHelper.getMessageSender().sendPartyJoinUpdate(request);
    }

    /**
     * Receive a party quit update
     * Allowing us to update all parties on all severs
     *
     * @param event
     * @throws IOException
     */
    public void receivePartyQuitUpdate(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();

        BungeeHelper.getMessageSender().sendPartyQuitUpdate(request);
    }

    /**
     * Receive a party chat toggle containing the toggle value, and the party UUID
     *
     * @param event
     * @throws IOException
     */
    public void receivePartyChatToggle(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);

        String[] request = in.readUTF().split(":");
        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        boolean value = Boolean.valueOf(request[1]);
        if (value) {
            UUID partyID = UUID.fromString(request[2]);
            BungeeHelper.getPartyChat().setPartyChatID(player, partyID);
        } else {
            BungeeHelper.getPartyChat().removePartyUUID(player);
        }
        BungeeHelper.getPartyChat().setPartyChatValue(player, value);
    }

    /**
     * Receive a party invite
     *
     * @param event
     * @throws IOException
     */
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
            sender.sendMessage(Messages.getMessage("party.invite-no-name"));
            return;
        }
        if (receiver.getUniqueId().equals(sender.getUniqueId())) {
            sender.sendMessage(Messages.getMessage("party.invite-self"));
            return;
        }
        sender.sendMessage(Messages.getMessage("party.invite-player", new Pair("%player%", receiver.getName())));
        receiver.sendMessage(Messages.getMessage("invite-received", new Pair("%player%", sender.getName())));
        BungeeHelper.getMessageSender().sendPartyInvite(partyID, receiver);
    }

    /**
     * Receive a party chat message
     * and send it off to all party members
     *
     * @param event
     * @throws IOException
     */
    public void receivePartyChatMessage(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String request = in.readUTF();
        BungeeHelper.getMessageSender().sendPartyChatMessage(request);
    }

    /**
     * Receive a death alert and send the dying player a title
     *
     * @param event
     * @throws IOException
     */
    public void receiveDeathAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String[] request = in.readUTF().split(":");

        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(request[0]));
        int wave = Integer.parseInt(request[1]);


        BungeeHelper.getTitleManager().sendDeathTitle(wave, player);
    }

    /**
     * Receive a camper kick alert
     * Add a camper to the list of kicked players
     * On lobby login send them the kick warning title
     *
     * @param event
     * @throws IOException
     */
    public void receiveCamperKickAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        ProxiedPlayer player = BungeeHelper.getInstance().getProxy().getPlayer(UUID.fromString(in.readUTF()));

        if (player != null)
            BungeeHelper.getKickManager().addCamper(player);
    }

    /**
     * Receive a violation alert from players with high NCP violation levels
     * Send the violator message to all online admins
     *
     * @param event
     * @throws IOException
     */
    public void receiveViolationAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        String violator = in.readUTF();

        for (ProxiedPlayer player : BungeeHelper.getInstance().getProxy().getPlayers()) {
            if (player.hasPermission("bungeecord.violations.recieve")) {
                player.sendMessage(Messages.getMessage("alerts.violation-alerts", new Pair("%player%", violator)));
            }
        }
    }

    /**
     * Receive the purchase alert and give the purchaser a title
     *
     * @param event
     * @throws IOException
     */
    public void receivePurchaseAlert(PluginMessageEvent event) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(in.readUTF()));
        if (player == null)
            return;
        BungeeHelper.getTitleManager().handlePurchaseAlert(player);
    }

    /**
     * Receive an arena start alert
     * Give all arena players a start title
     *
     * @param event
     * @throws IOException
     */
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
