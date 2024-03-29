package com.mobarenas.bungeecord.listeners;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;

public class MessageReceiveListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) throws IOException {

        if (!(event.getSender() instanceof Server))
            return;

        switch (event.getTag()) {
            case "party-status-request":
                BungeeHelper.getMessageReceiver().receiveStatusRequest(event);
                break;
            case "party-join-update":
                BungeeHelper.getMessageReceiver().receivePartyJoinUpdate(event);
                break;
            case "party-quit-update":
                BungeeHelper.getMessageReceiver().receivePartyQuitUpdate(event);
                break;
            case "party-chat-toggle":
                BungeeHelper.getMessageReceiver().receivePartyChatToggle(event);
                break;
            case "party-login-update":
                BungeeHelper.getMessageReceiver().receiveLoginUpdate(event);
                break;
            case "party-invite-player":
                BungeeHelper.getMessageReceiver().receivePartyInvite(event);
                break;
            case "party-chat-channel":
                BungeeHelper.getMessageReceiver().receivePartyChatMessage(event);
                break;
            case "death-alerts":
                BungeeHelper.getMessageReceiver().receiveDeathAlert(event);
                break;
            case "camp-kick-alerts":
                BungeeHelper.getMessageReceiver().receiveCamperKickAlert(event);
                break;
            case "violation-alerts":
                BungeeHelper.getMessageReceiver().receiveViolationAlert(event);
                break;
            case "purchase-alerts":
                BungeeHelper.getMessageReceiver().receivePurchaseAlert(event);
                break;
            case "arena-start-alerts":
                BungeeHelper.getMessageReceiver().receiveArenaStartAlert(event);
                break;
            case "timer-alerts":
                BungeeHelper.getMessageReceiver().receiveTimerAlert(event);
                break;
            case "ability-activate":
                BungeeHelper.getMessageReceiver().receiveAbilityAlert(event);
                break;
            case "final-wave":
                BungeeHelper.getMessageReceiver().receiveFinalWaveAlert(event);
                break;
            case "game-won":
                BungeeHelper.getMessageReceiver().receiveGameWonAlert(event);
                break;
            case "crate-given":
                BungeeHelper.getMessageReceiver().receiveCrate(event);
                break;
            case "crate-alerts":
                BungeeHelper.getMessageReceiver().receiveCrateUnlock(event);
                break;

        }
    }
}
