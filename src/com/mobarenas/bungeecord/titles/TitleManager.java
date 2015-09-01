package com.mobarenas.bungeecord.titles;

import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TitleManager {

    /**
     * Send the given player a new death title
     *
     * @param wave   the player died on
     * @param player to send the title to
     */
    public void sendDeathTitle(int wave, ProxiedPlayer player) {
        Title title = TitleCreator.createDeathTitle(wave);

        title.send(player);
        title.reset();
    }

    /**
     * Send the given player a login alert
     *
     * @param player to send the login alert to
     */
    public void handleLoginAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createLoginTitle();

        title.send(player);
        title.reset();
    }

    /**
     * Send the player a camper kick alert
     *
     * @param player to send the new kick title for
     */
    public void handleKickAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createKickedTitle();

        title.send(player);
        title.reset();
    }

    /**
     * Send the given player a purchase alert
     *
     * @param player to send the purhcase alert to
     */
    public void handlePurchaseAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createPurchaseTitle();

        title.send(player);
        title.reset();
    }

    /**
     * Send the given player an arena start alert for the given arena
     *
     * @param arenaName name of arena for the title params
     * @param player to send the start title to
     */
    public void handleStartAlert(String arenaName, ProxiedPlayer player) {
        Title title = TitleCreator.createStartTitle(arenaName);
        title.send(player);
        title.reset();
    }
}
