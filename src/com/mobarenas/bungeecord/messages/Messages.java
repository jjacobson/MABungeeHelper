package com.mobarenas.bungeecord.messages;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by HP1 on 8/30/2015.
 */
public class Messages {

    private BungeeHelper plugin;
    private static Configuration messages;

    public Messages() throws IOException {
        this.plugin = BungeeHelper.getInstance();
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            Files.copy(plugin.getResourceAsStream("messages.yml"), file.toPath());
        }
        messages = ConfigurationProvider.getProvider(YamlConfiguration.class)
                .load(new File(plugin.getDataFolder(), "config.yml"));

        ConfigurationProvider.getProvider(YamlConfiguration.class)
                .save(messages, new File(plugin.getDataFolder(), "messages.yml"));
    }

    public static BaseComponent[] getMessage(String path) {
        BaseComponent[] text = TextComponent.fromLegacyText(colorize(messages.getString(path, "&cCouldn't find message (path: " + path + ")")));
        return text;
    }

    public static BaseComponent[] getMessage(String path, Pair... replace) {
        BaseComponent[] text = TextComponent.fromLegacyText(replace(colorize(messages.getString(path, "&cCouldn't find message (path: " + path + ")"))));
        return text;
    }

    private static String replace(String message, Pair... replace) {
        for (Pair pair : replace) {
            message = message.replace(pair.getKey(), pair.getValue());
        }
        return message;
    }

    private static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
