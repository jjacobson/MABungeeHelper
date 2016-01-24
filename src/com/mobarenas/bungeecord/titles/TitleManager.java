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
        Title title = TitleCreator.createLoginTitle(player);

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
        Title title = TitleCreator.createStartTitle(arenaName.replace('-', ' '));
        title.send(player);
        title.reset();
    }

    public void handleTimerAlert(ProxiedPlayer player, int time) {
        Title title = TitleCreator.createTimerTitle(time);
        title.send(player);
        title.reset();
    }


    public void handleAbilityAlert(String ability, ProxiedPlayer player) {
        Title title = TitleCreator.createAbilityTitle(ability);
        title.send(player);
        title.reset();
    }

    public void handleFinalWaveAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createFinalWaveTitle();
        title.send(player);
        title.reset();
    }

    public void handleGameWonAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createGameWonTitle();
        title.send(player);
        title.reset();
    }

    public void handleCrateUnlock(String title, String subtitle, ProxiedPlayer player) {
        Title t = TitleCreator.createCrateTitle(title, subtitle);
        t.send(player);
        t.reset();
    }
}
