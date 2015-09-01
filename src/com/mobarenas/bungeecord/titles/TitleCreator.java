package com.mobarenas.bungeecord.titles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TitleCreator {

    public static Title createDeathTitle(int wave) {

        Title screenTitle = ProxyServer.getInstance().createTitle();
        TextComponent title = new TextComponent("You are dead");
        title.setColor(ChatColor.RED);
        screenTitle.title(title);
        BaseComponent[] subtitle;
        if (wave == 1) {
            subtitle = new ComponentBuilder("You survived for ").color(ChatColor.GOLD).append(Integer.toString(wave)).color(ChatColor.AQUA).append(" wave").color(ChatColor.GOLD).create();
        } else {
            subtitle = new ComponentBuilder("You survived for ").color(ChatColor.GOLD).append(Integer.toString(wave)).color(ChatColor.AQUA).append(" waves").color(ChatColor.GOLD).create();
        }

        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(60);
        screenTitle.fadeOut(10);

        return screenTitle;

    }

    public static Title createLoginTitle(ProxiedPlayer player) {

        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = new ComponentBuilder("MobArenas").color(ChatColor.AQUA).create();
        BaseComponent[] subtitle = new ComponentBuilder("Welcome ").color(ChatColor.RED).append(player.getName()).color(ChatColor.AQUA).append(", enter the").color(ChatColor.RED).append(" portal ")
                .color(ChatColor.AQUA).append("to join the game").color(ChatColor.RED).create();

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(80);
        screenTitle.fadeOut(20);

        return screenTitle;
    }

    public static Title createKickedTitle() {

        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = new ComponentBuilder("Warning").color(ChatColor.RED).create();
        BaseComponent[] subtitle = new ComponentBuilder("You were kicked from the game for inactivity").color(ChatColor.RED).create();

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(80);
        screenTitle.fadeOut(20);

        return screenTitle;
    }

    public static Title createPurchaseTitle() {

        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = new ComponentBuilder("Purchase Received").color(ChatColor.AQUA).create();
        BaseComponent[] subtitle = new ComponentBuilder("Your purchase has been added to your account. ").color(ChatColor.GREEN).create();

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(20);
        screenTitle.stay(100);
        screenTitle.fadeOut(20);

        return screenTitle;
    }

    public static Title createStartTitle(String arena) {
        Title startTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = new ComponentBuilder("Welcome to").color(ChatColor.AQUA).create();
        BaseComponent[] subtitle = new ComponentBuilder(arena).color(ChatColor.RED).create();

        startTitle.title(title);
        startTitle.subTitle(subtitle);

        startTitle.fadeIn(20);
        startTitle.stay(100);
        startTitle.fadeOut(20);

        return startTitle;

    }
}
