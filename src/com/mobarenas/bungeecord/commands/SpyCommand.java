package com.mobarenas.bungeecord.commands;

import com.mobarenas.bungeecord.BungeeHelper;
import com.mobarenas.bungeecord.messages.Messages;
import net.md_5.bungee.api.CommandSender;
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
            playerSender.sendMessage(Messages.getMessage("spy.args"));
            return;
        }

        if (BungeeHelper.getSpyManager().isSpy(playerSender)) {
            BungeeHelper.getSpyManager().removeSpy(playerSender);
            playerSender.sendMessage(Messages.getMessage("spy.toggle-off"));
        } else {
            BungeeHelper.getSpyManager().addSpy(playerSender);
            playerSender.sendMessage(Messages.getMessage("spy.toggle-on"));
        }
    }

}
