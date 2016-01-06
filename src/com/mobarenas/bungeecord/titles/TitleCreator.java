package com.mobarenas.bungeecord.titles;

import com.mobarenas.bungeecord.messages.Messages;
import com.mobarenas.bungeecord.messages.Pair;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.BaseComponent;

public class TitleCreator {

    /**
     * Create a new death title with the given wave
     *
     * @param wave the player died on
     * @return a new death title with the given parameters
     */
    public static Title createDeathTitle(int wave) {
        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.death.title");
        BaseComponent[] subtitle;
        if (wave == 1) {
            subtitle = Messages.getMessage("alerts.death.subtitle-1");
        } else {
            subtitle = Messages.getMessage("alerts.death.subtitle", new Pair("%wave%", wave + ""));
        }

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(60);
        screenTitle.fadeOut(10);
        return screenTitle;
    }

    /**
     * Create a new login title
     *
     * @return a new login title
     */
    public static Title createLoginTitle() {
        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.login.title");
        BaseComponent[] subtitle = Messages.getMessage("alerts.login.subtitle");

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(80);
        screenTitle.fadeOut(20);
        return screenTitle;
    }

    /**
     * Create a kicked title for a camper
     *
     * @return a new camper kicked title
     */
    public static Title createKickedTitle() {
        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.kicked.title");
        BaseComponent[] subtitle = Messages.getMessage("alerts.kicked.subtitle");

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(0);
        screenTitle.stay(80);
        screenTitle.fadeOut(20);
        return screenTitle;
    }

    /**
     * Create a new purchase title
     *
     * @return a new purchase title
     */
    public static Title createPurchaseTitle() {
        Title screenTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.purchase.title");
        BaseComponent[] subtitle = Messages.getMessage("alerts.purchase.subtitle");

        screenTitle.title(title);
        screenTitle.subTitle(subtitle);

        screenTitle.fadeIn(20);
        screenTitle.stay(100);
        screenTitle.fadeOut(20);
        return screenTitle;
    }

    /**
     * Create a new arena start title for the given arena
     *
     * @param arena that started
     * @return a arena start title for the given arena
     */
    public static Title createStartTitle(String arena) {
        Title startTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.start.title");
        BaseComponent[] subtitle = Messages.getMessage("alerts.start.subtitle", new Pair("%arena%", arena));

        startTitle.title(title);
        startTitle.subTitle(subtitle);

        startTitle.fadeIn(20);
        startTitle.stay(60);
        startTitle.fadeOut(20);
        return startTitle;
    }

    /**
     * Create a new crate title with the given title and subtitle
     *
     * @param top
     * @param bottom
     * @return
     */
    public static Title createCrateTitle(String top, String bottom) {
        Title startTitle = ProxyServer.getInstance().createTitle();

        BaseComponent[] title = Messages.getMessage("alerts.crate.title", new Pair("%title%", top));
        BaseComponent[] subtitle = Messages.getMessage("alerts.crate.subtitle", new Pair("%subtitle%", bottom));

        startTitle.title(title);
        startTitle.subTitle(subtitle);

        startTitle.fadeIn(20);
        startTitle.stay(60);
        startTitle.fadeOut(20);
        return startTitle;
    }
}
