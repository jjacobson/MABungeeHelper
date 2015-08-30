package com.mobarenas.bungeecord;

import com.mobarenas.bungeecord.commands.MessageCommand;
import com.mobarenas.bungeecord.commands.ReplyCommand;
import com.mobarenas.bungeecord.commands.SpyCommand;
import com.mobarenas.bungeecord.listeners.*;
import com.mobarenas.bungeecord.utils.MessageHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BungeeHelper extends Plugin {

    private static BungeeHelper instance;
    private Map<ProxiedPlayer, ProxiedPlayer> replyList = new HashMap<ProxiedPlayer, ProxiedPlayer>();
    private Map<ProxiedPlayer, Boolean> partyChat = new HashMap<>();
    private List<ProxiedPlayer> spies = new ArrayList<ProxiedPlayer>();
    private List<ProxiedPlayer> kicked = new ArrayList<ProxiedPlayer>();
    private MessageHandler handler;

    public static BungeeHelper getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        handler = new MessageHandler();
        this.getProxy().registerChannel("death-alerts");
        this.getProxy().registerChannel("camp-kick-alerts");
        this.getProxy().registerChannel("violation-alerts");
        this.getProxy().registerChannel("purchase-alerts");
        this.getProxy().registerChannel("party-status-request");
        this.getProxy().registerChannel("party-chat-channel");
        this.getProxy().registerChannel("party-invite-player");
        this.getProxy().registerChannel("arena-start-alerts");
        this.getProxy().registerChannel("party-chat-toggle");
        this.getProxy().getPluginManager().registerListener(this, new BungeeChat());
        this.getProxy().getPluginManager().registerListener(this, new BungeeJoin());
        this.getProxy().getPluginManager().registerListener(this, new BungeeLeave());
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageReceive());
        this.getProxy().getPluginManager().registerListener(this, new ServerSwitch());
        this.getProxy().getPluginManager().registerListener(this, new TabComplete());
        getProxy().getPluginManager().registerCommand(this, new MessageCommand());
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand());
        getProxy().getPluginManager().registerCommand(this, new SpyCommand());
    }

    public MessageHandler getMessageHandler() {
        return handler;
    }

    public Map<ProxiedPlayer, ProxiedPlayer> getProxiedPlayerList() {
        return replyList;
    }

    public void removePlayer(ProxiedPlayer player) {
        replyList.remove(player);

    }

    public void addPlayer(ProxiedPlayer sender, ProxiedPlayer reciever) {
        replyList.put(sender, reciever);
    }

    public boolean containsPlayer(ProxiedPlayer player) {
        return replyList.containsKey(player);
    }

    public void addSpy(ProxiedPlayer player) {
        if (!spies.contains(player))
            spies.add(player);
    }

    public void removeSpy(ProxiedPlayer player) {
        if (spies.contains(player))
            spies.remove(player);
    }

    public List<ProxiedPlayer> getSpies() {
        return spies;
    }

    public boolean isSpy(ProxiedPlayer player) {
        return spies.contains(player);
    }

    public boolean hasBeenKicked(ProxiedPlayer player) {
        return kicked.contains(player);

    }

    public void removeKickedPlayer(ProxiedPlayer player) {
        kicked.remove(player);
    }

    public void addKickedPlayer(ProxiedPlayer player) {
        kicked.add(player);
    }

    public boolean getChatValue(ProxiedPlayer player) {
        if (!partyChat.containsKey(player))
            return false;

        return partyChat.get(player);
    }

    public void setChatValue(ProxiedPlayer player, boolean value) {
        partyChat.put(player, value);
    }

    public void removeChatValue(ProxiedPlayer player) {
        partyChat.remove(player);
    }


}
