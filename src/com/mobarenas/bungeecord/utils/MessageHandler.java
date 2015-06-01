package com.mobarenas.bungeecord.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageHandler {

    public void handleDeathAlert(String alert) {
	
	String[] msg = alert.split(":");
	
	ProxiedPlayer player = BungeeCord.getInstance().getPlayer(msg[0]);
	int wave = Integer.parseInt(msg[1]);
		
	Title title = TitleHelper.createDeathTitle(wave);
	
	title.send(player);
	title.reset();
	
    }

    public void handleLoginAlert(ProxiedPlayer player) {
	
	Title title = TitleHelper.createLoginTitle(player);
	
	title.send(player);
	title.reset();
	
    }

    public void handleKickAlert(ProxiedPlayer player) {

	Title title = TitleHelper.createKickedTitle();
	
	title.send(player);
	title.reset();
	
    }
    
    
    
    

}
