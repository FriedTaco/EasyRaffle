package com.FriedTaco.taco.EasyRaffle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RaffleCommand implements CommandExecutor
{
	private Player player;
	private final EasyRaffle plugin;
    public RaffleCommand(EasyRaffle instance) 
    {
        plugin = instance;
    }

    private ItemStack item;
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
    	if(sender instanceof Player)
    	{
    		player = (Player) sender;
    		if((EasyRaffle.Permissions == null && player.isOp()) || (EasyRaffle.Permissions != null && EasyRaffle.Permissions.has(player, "EasyRaffle.raffle")))
    		{
    			if(args.length == 2)
    			{
    				if(!EasyRaffle.raffleInProgress)
    				{
    					if(!isInt(args[1]))
    					{
    						player.sendMessage("Incorrect syntax, use '/raffle [item] [amount]'");
    						return true;
    					}
    					ItemStack[] inv = player.getInventory().getContents();
    					int amount = Integer.parseInt(args[1]);
	    				int index = -1;
	    				int id = 0;
    					for(ItemStack i : inv)
    					{
    						if(i != null && i.getType().name().equalsIgnoreCase(args[0]))
    						{
    							id = i.getTypeId();
    							if(i.getAmount() >= amount)
	    						{
	    							index = player.getInventory().first(i);
	    							i.setAmount(i.getAmount()-amount);
	    							break;
	    						}
    						}	
    					}
    					if (id == 0 && isInt(args[0]))
    					{
    						id = Integer.parseInt(args[0]);
	    					for(ItemStack i : inv)
	    					{
	    						if(i != null && i.getTypeId() == id)
	    						{
	    							if(i.getAmount() >= amount)
	    							{
	    								index = player.getInventory().first(i);
	    								i.setAmount(i.getAmount()-amount);
	    								break;
	    							}
	    							else if(index != -1)
	    								break;
	    						}
	    					}
    					}
	    				if(index != -1)
	    				{
	    						player.getInventory().setContents(inv);
		    					item = new ItemStack(id, amount);
			    				for(Player p : player.getServer().getOnlinePlayers())
			    					p.sendMessage(player.getName() + " has started a raffle for " + amount + " " + item.getType().name().toLowerCase() + "! One lucky person will be picked in five minutes!");
			    				EasyRaffle.raffleInProgress= true;
			    				@SuppressWarnings("unused")
								RaffleAward raffle = new RaffleAward();

	    				}
	    				else
	    				{
	    					player.sendMessage("You don't have that item!");
	    				}
    				return true;
    				}
    				else
    				{
    					player.sendMessage("There's already a raffle in progress!");
    					return true;
    				}
    			}
    			else
    			{
    				player.sendMessage("Incorrect syntax.");
    				return true;
    			}
    		}
    	}
		return false;
    }
    public void awardItem()
    {
    	
    	Player winner = plugin.getServer().getOnlinePlayers()[(int)(Math.random()*EasyRaffle.onlinePlayers.size())];
    	winner.sendMessage("Congrats! You have won the raffle! The prize has been dropped at your feet.");
    	winner.getWorld().dropItemNaturally(winner.getLocation(), item);
    	for(Player p : player.getServer().getOnlinePlayers())
			p.sendMessage("The raffle is over and " + winner.getName() + " has won!");
    }
     public boolean isInt(String test)  
     {  
        try  
        {  
           Integer.parseInt(test);  
           return true;  
        }  
        catch(Exception e)  
        {  
          return false;  
        }  
    }  
    class RaffleAward extends TimerTask implements ActionListener
    {
    	Timer RaffleTimer = new Timer(300000, this);
    	public RaffleAward()
    	{
    		RaffleTimer.start();
    		RaffleTimer.setRepeats(false);
    	}
    	public void actionPerformed(ActionEvent e) 
    	{
    		awardItem();
    		EasyRaffle.raffleInProgress = false;
    	}
    	@Override
    	public void run() 
    	{
    		
    	}
    }
}