package com.FriedTaco.taco.EasyRaffle;


import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;



public class EasyRafflePlayerListener extends PlayerListener 
{
	final EasyRaffle plugin;

    public EasyRafflePlayerListener(EasyRaffle instance) 
    {
        plugin = instance;
    }

    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) 
    {
    	EasyRaffle.onlinePlayers.add(event.getPlayer());
    }
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) 
    {
    	EasyRaffle.onlinePlayers.remove(event.getPlayer());
    }
   }


