package com.mobarenas.bungeecord;

import com.mobarenas.bungeecord.campmanager.KickManager;
import com.mobarenas.bungeecord.commands.MessageCommand;
import com.mobarenas.bungeecord.commands.ReplyCommand;
import com.mobarenas.bungeecord.commands.SpyCommand;
import com.mobarenas.bungeecord.listeners.*;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.parties.PartyChat;
import com.mobarenas.bungeecord.privatemessaging.MessageManager;
import com.mobarenas.bungeecord.servercommunications.ReceiveMessage;
import com.mobarenas.bungeecord.servercommunications.SendMessage;
import com.mobarenas.bungeecord.spy.SpyManager;
import com.mobarenas.bungeecord.titles.TitleManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

public class BungeeHelper extends Plugin {

    private static BungeeHelper instance;
    private static SpyManager spyManager;
    private static MessageManager messageManager;
    private static KickManager kickManager;
    private static PartyChat partyChat;
    private static ReceiveMessage receiveMessage;
    private static SendMessage sendMessage;
    private static TitleManager titleManager;

    public void onEnable() {
        instance = this;
        registerHelpers();
        registerChannels();
        registerListeners();
        registerCommands();
    }

    /**
     * Register the bungeecord helper classes
     */
    private void registerHelpers() {
        try {
            new Messages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleManager = new TitleManager();
        messageManager = new MessageManager();
        spyManager = new SpyManager();
        kickManager = new KickManager();
        partyChat = new PartyChat();
        receiveMessage = new ReceiveMessage();
        sendMessage = new SendMessage();
    }

    /**
     * Register the channels that bungeecord can send/receive data on
     */
    private void registerChannels() {
        this.getProxy().registerChannel("death-alerts");
        this.getProxy().registerChannel("camp-kick-alerts");
        this.getProxy().registerChannel("violation-alerts");
        this.getProxy().registerChannel("purchase-alerts");
        this.getProxy().registerChannel("party-status-request");
        this.getProxy().registerChannel("party-chat-channel");
        this.getProxy().registerChannel("party-invite-player");
        this.getProxy().registerChannel("arena-start-alerts");
        this.getProxy().registerChannel("party-chat-toggle");
        this.getProxy().registerChannel("party-quit-update");
        this.getProxy().registerChannel("party-join-update");
    }

    /**
     * Register the bungeecord listeners
     */
    private void registerListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PlayerChatListener());
        this.getProxy().getPluginManager().registerListener(this, new PlayerJoinListener());
        this.getProxy().getPluginManager().registerListener(this, new PlayerQuitListener());
        this.getProxy().getPluginManager().registerListener(this, new MessageReceiveListener());
        this.getProxy().getPluginManager().registerListener(this, new ServerSwitchListener());
        this.getProxy().getPluginManager().registerListener(this, new TabCompleteListener());
    }

    /**
     * Register the bungeecord commands
     */
    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new MessageCommand());
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand());
        getProxy().getPluginManager().registerCommand(this, new SpyCommand());
    }

    /**
     * Get the plugin instance
     *
     * @return the instance of this bungeecord plugin
     */
    public static BungeeHelper getInstance() {
        return instance;
    }

    public static SpyManager getSpyManager() {
        return spyManager;
    }

    public static KickManager getKickManager() {
        return kickManager;
    }

    public static PartyChat getPartyChat() {
        return partyChat;
    }

    public static MessageManager getMessageManager() {
        return messageManager;
    }

    public static ReceiveMessage getMessageReceiver() {
        return receiveMessage;
    }

    public static SendMessage getMessageSender() {
        return sendMessage;
    }

    public static TitleManager getTitleManager() {
        return titleManager;
    }
}
