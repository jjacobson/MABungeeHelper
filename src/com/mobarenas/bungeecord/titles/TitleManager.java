package com.mobarenas.bungeecord.titles;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
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


    public void handlePurchaseAlert(String player) {

        ProxiedPlayer p = BungeeCord.getInstance().getPlayer(player);

        if (p == null)
            return;

        Title title = TitleCreator.createPurchaseTitle();
        title.send(p);
        title.reset();
    }

    public List<UUID> handlePartyStatusRequest(List<UUID> request) {
        List<UUID> response = new ArrayList<>();
        for (UUID id : request) {
            if (BungeeHelper.getInstance().getProxy().getPlayer(id) != null)
                response.add(id);
        }
        return response;
    }

    public void handleStartAlert(String arenaName, ProxiedPlayer player) {
        Title title = TitleCreator.createStartTitle(arenaName);
        title.send(player);
        title.reset();
    }
}
