package com.mobarenas.bungeecord.titles;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class TitleManager {

    public void sendDeathTitle(int wave, ProxiedPlayer player) {
        Title title = TitleCreator.createDeathTitle(wave);

        title.send(player);
        title.reset();
    }

    public void handleLoginAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createLoginTitle(player);

        title.send(player);
        title.reset();
    }

    public void handleKickAlert(ProxiedPlayer player) {
        Title title = TitleCreator.createKickedTitle();

        title.send(player);
        title.reset();
    }


    public void handlePurchaseAlert(String uuid) {
        ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(uuid));

        if (player == null)
            return;

        Title title = TitleCreator.createPurchaseTitle();
        title.send(player);
        title.reset();
    }

    public void handleStartAlert(String arenaName, ProxiedPlayer player) {
        Title title = TitleCreator.createStartTitle(arenaName);
        title.send(player);
        title.reset();
    }
}
