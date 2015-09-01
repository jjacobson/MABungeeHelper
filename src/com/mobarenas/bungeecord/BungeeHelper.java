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

    /**
     * Get the plugin instance
     *
     * @return the instance of this bungeecord plugin
     */
    public static BungeeHelper getInstance() {
        return instance;
    }

    /**
     * Get the spy manager for the server
     *
     * @return the servers spy manager
     */
    public static SpyManager getSpyManager() {
        return spyManager;
    }

    /**
     * Get the camper kick manager for the server
     *
     * @return the camper kick manager
     */
    public static KickManager getKickManager() {
        return kickManager;
    }

    /**
     * Get the party chat messenger
     *
     * @return servers party chat manager
     */
    public static PartyChat getPartyChat() {
        return partyChat;
    }

    /**
     * Get the private message manager for the server
     *
     * @return servers private message manager
     */
    public static MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Get the plugin message receiver for the server
     *
     * @return servers plugin message receiver
     */
    public static ReceiveMessage getMessageReceiver() {
        return receiveMessage;
    }

    /**
     * Get the servers plugin message sender
     *
     * @return plugin message sender for the server
     */
    public static SendMessage getMessageSender() {
        return sendMessage;
    }

    /**
     * Get the title manager for the server
     *
     * @return servers title manager
     */
    public static TitleManager getTitleManager() {
        return titleManager;
    }

    @Override
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
        this.getProxy().registerChannel("party-login-update");
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
}
