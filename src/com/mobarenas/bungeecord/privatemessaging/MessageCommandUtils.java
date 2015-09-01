package com.mobarenas.bungeecord.privatemessaging;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.messages.Pair;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageCommandUtils {

    /**
     * Send a private message, notifying both the player and the sender
     *
     * @param sender   of the message
     * @param receiver of the message
     * @param message  sent by sender
     */
    public static void sendPlayerMessage(ProxiedPlayer sender, ProxiedPlayer receiver, String message) {
        sender.sendMessage(Messages.getMessage("message.private-send", new Pair("%receiver%", receiver.getName()), new Pair("%message%", ChatColor.stripColor(message))));
        receiver.sendMessage(Messages.getMessage("message.private-receive", new Pair("%sender%", sender.getName()), new Pair("%message%", ChatColor.stripColor(message))));

        for (ProxiedPlayer player : BungeeHelper.getSpyManager().getSpies()) {
            if (player == sender || player == receiver)
                continue;

            player.sendMessage(Messages.getMessage("spy.private-message",
                    new Pair("%sender%", sender.getName()),
                    new Pair("%receiver%", receiver.getName()),
                    new Pair("%message%", ChatColor.stripColor(message))));
        }
    }

    /**
     * Set the player senders so reply will correctly message a player
     *
     * @param sender   message sender
     * @param receiver message receiver
     */
    public static void setPlayerSenders(ProxiedPlayer sender, ProxiedPlayer receiver) {
        BungeeHelper.getMessageManager().addMessenger(sender, receiver);
        BungeeHelper.getMessageManager().addMessenger(receiver, sender);
    }

}
