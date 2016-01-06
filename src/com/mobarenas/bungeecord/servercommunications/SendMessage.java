package com.mobarenas.bungeecord.servercommunications;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Created by HP1 on 8/30/2015.
 */
public class SendMessage {

    private BungeeHelper plugin;

    public SendMessage() {
        plugin = BungeeHelper.getInstance();
    }

    /**
     * Update servers to respect a players party chat status
     *
     * @param player     who toggled their party chat
     * @param serverInfo server to send it to
     */
    public void sendPartyChatStatus(ProxiedPlayer player, ServerInfo serverInfo) {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        try {
            out.writeUTF(player.getUniqueId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverInfo.sendData("party-chat-toggle", responseStream.toByteArray());
    }

    /**
     * Send the status response for currently online bungeecord players
     *
     * @param sender   who requested the status
     * @param response list of players who are online bungeecord and in the party
     * @throws IOException
     */
    public void sendPlayerStatusResponse(ProxiedPlayer sender, List<UUID> response) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);

        StringJoiner joiner = new StringJoiner(";");
        for (UUID id : response) {
            joiner.add(id.toString());
        }
        out.writeUTF(sender.getUniqueId().toString() + ":" + joiner.toString());
        sender.getServer().getInfo().sendData("party-status-request", responseStream.toByteArray());
    }

    /**
     * Send a party join update to all servers so that they can alert players and modify their party list
     *
     * @param request to send to servers
     * @throws IOException
     */
    public void sendPartyJoinUpdate(String request) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(request);

        for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
            if (info.getPlayers().size() == 0)
                continue;
            info.sendData("party-join-update", responseStream.toByteArray());
        }
    }

    /**
     * Send a party quit update so all servers can alert party players and modify their party list
     *
     * @param request to send to servers
     * @throws IOException
     */
    public void sendPartyQuitUpdate(String request) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(request);

        for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
            if (info.getPlayers().size() == 0)
                continue;
            info.sendData("party-quit-update", responseStream.toByteArray());
        }
    }

    /**
     * Send a party invite to the server that contains the receiving player
     *
     * @param partyID  of party to invite a player to
     * @param receiver of the party invite
     * @throws IOException
     */
    public void sendPartyInvite(String partyID, ProxiedPlayer receiver) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(partyID + ":" + receiver.getUniqueId().toString());
        receiver.getServer().getInfo().sendData("party-invite-player", responseStream.toByteArray());
    }

    /**
     * Send a party chat message to servers so they can message party members
     *
     * @param request to send to servers
     * @throws IOException
     */
    public void sendPartyChatMessage(String request) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(request);

        for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
            // send once to each server
            for (ProxiedPlayer player : info.getPlayers()) {
                player.getServer().getInfo().sendData("party-chat-channel", responseStream.toByteArray());
                break;
            }
        }
    }

    /**
     * Send a chest to the lobby
     *
     * @param uuid of receiver
     * @throws IOException
     */
    public void giveChest(UUID uuid) throws IOException {
        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(uuid);
        if (player != null) {
            BungeeHelper.getChestManager().addReceiver(player);
        }
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(uuid.toString());
        for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
            if (info.getName().equalsIgnoreCase("lobby")) {
                info.sendData("crate-received", responseStream.toByteArray());
                break;
            }
        }
    }
}
