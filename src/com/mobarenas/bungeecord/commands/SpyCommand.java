package com.mobarenas.bungeecord.commands;

import com.mobarenas.bungeecord.BungeeHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SpyCommand extends Command {

    private BungeeHelper plugin;

    public SpyCommand() {
        super("spy", "bungeecord.command.spy", "chatspy");
        this.plugin = BungeeHelper.getInstance();
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if (!(commandSender instanceof ProxiedPlayer))
            return;

        ProxiedPlayer playerSender = (ProxiedPlayer) commandSender;

        if (args.length != 0) {
            playerSender.sendMessage(new ComponentBuilder("Error: correct usage /spy").color(ChatColor.RED).create());
            return;
        }

        if (plugin.getSpyManager().isSpy(playerSender)) {
            plugin.getSpyManager().removeSpy(playerSender);
            playerSender.sendMessage(new ComponentBuilder("You are no longer spying on all players").color(ChatColor.GOLD).create());
        } else {
            plugin.getSpyManager().addSpy(playerSender);
            playerSender.sendMessage(new ComponentBuilder("You are now spying on all players").color(ChatColor.GOLD).create());
        }
    }

}
