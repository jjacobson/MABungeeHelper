package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.messages.Pair;
import com.mobarenas.bungeecord.parties.PartyChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void playerChat(ChatEvent event) {

        if (!(event.getSender() instanceof ProxiedPlayer))
            return;

        if (event.isCommand())
            return;

        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
        for (ProxiedPlayer spy : BungeeHelper.getSpyManager().getSpies()) {
            if (sender == spy)
                continue;

            // Handle party chat
            if (BungeeHelper.getPartyChat().isInPartyChat(sender)) {
                chatSpyParty(sender, spy, event.getMessage());
                return;
            }

            // Dont send to players on the same server
            if (sender.getServer().getInfo().getName().equals(spy.getServer().getInfo().getName()))
                continue;

            String server = (sender.getServer().getInfo().getName().startsWith("slave")) ? "GAME" : "LOBBY";
            spy.sendMessage(Messages.getMessage("spy.spy-message",
                    new Pair("%server%", server),
                    new Pair("%player%", sender.getName()),
                    new Pair("%message%", ChatColor.stripColor(event.getMessage()))));
        }

    }

    /**
     * Handle party chat
     *
     * @param sender  who sent the message
     * @param spy     person spying
     * @param message party message
     */
    private void chatSpyParty(ProxiedPlayer sender, ProxiedPlayer spy, String message) {
        PartyChat partyChat = BungeeHelper.getPartyChat();

        if (partyChat.getPartyChatID(sender) == null) {
            System.out.println("sender null");
        }
        if (partyChat.getPartyChatID(spy) == null) {
            System.out.println("spy null");
        }


        if (partyChat.getPartyChatID(spy) != null) {
            // dont send if they are in the same party
            if (partyChat.getPartyChatID(sender).equals(partyChat.getPartyChatID(spy)))
                return;
        }
        spy.sendMessage(Messages.getMessage("spy.party-message", new Pair("%player%", sender.getName()), new Pair("%message%", message)));
    }

}
