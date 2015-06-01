package com.mobarenas.bungeecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import com.mobarenas.bungeecord.commands.MessageCommand;
import com.mobarenas.bungeecord.commands.ReplyCommand;
import com.mobarenas.bungeecord.commands.SpyCommand;
import com.mobarenas.bungeecord.listeners.BungeeChat;
import com.mobarenas.bungeecord.listeners.BungeeJoin;
import com.mobarenas.bungeecord.listeners.BungeeLeave;
import com.mobarenas.bungeecord.listeners.PluginMessageRecieve;
import com.mobarenas.bungeecord.listeners.ServerSwitch;
import com.mobarenas.bungeecord.listeners.TabComplete;
import com.mobarenas.bungeecord.utils.MessageHandler;

public class BungeeHelper extends Plugin {

    private Map<ProxiedPlayer, ProxiedPlayer> replyList = new HashMap<ProxiedPlayer, ProxiedPlayer>();
    private List<ProxiedPlayer> spies = new ArrayList<ProxiedPlayer>();
    private List<ProxiedPlayer> kicked = new ArrayList<ProxiedPlayer>();

    private static BungeeHelper instance;
    private MessageHandler handler;

    public void onEnable() {
	instance = this;
	handler = new MessageHandler();
	this.getProxy().registerChannel("death-alerts");
	this.getProxy().registerChannel("camp-kick-alerts");
	this.getProxy().registerChannel("violation-alerts");
	this.getProxy().getPluginManager().registerListener(this, new BungeeChat());
	this.getProxy().getPluginManager().registerListener(this, new BungeeJoin());
	this.getProxy().getPluginManager().registerListener(this, new BungeeLeave());
	this.getProxy().getPluginManager().registerListener(this, new PluginMessageRecieve());
	this.getProxy().getPluginManager().registerListener(this, new ServerSwitch());
	this.getProxy().getPluginManager().registerListener(this, new TabComplete());
	getProxy().getPluginManager().registerCommand(this, new MessageCommand());
	getProxy().getPluginManager().registerCommand(this, new ReplyCommand());
	getProxy().getPluginManager().registerCommand(this, new SpyCommand());
    }

    public static BungeeHelper getInstance() {
	return instance;
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


}
