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

    public void sendPartyUpdate(String request) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(request);

        for (ServerInfo info : BungeeCord.getInstance().getServers().values()) {
            if (info.getPlayers().size() == 0)
                continue;
            info.sendData("party-join-update", responseStream.toByteArray());
        }
    }

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

    public void sendPartyInvite(String partyID, ProxiedPlayer receiver) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(responseStream);
        out.writeUTF(partyID + ":" + receiver.getUniqueId().toString());
        receiver.getServer().getInfo().sendData("party-invite-player", responseStream.toByteArray());
    }

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
}
