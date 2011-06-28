package com.FriedTaco.taco.EasyRaffle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.Timer;

public class RaffleAward extends TimerTask implements ActionListener
{
	Timer RaffleTimer = new Timer(300000, this);
	
	public RaffleAward()
	{
		RaffleTimer.start();
		RaffleTimer.setRepeats(false);
	}
	public void actionPerformed(ActionEvent e) 
	{
		EasyRaffle.raffleInProgress = false;
	}
	@Override
	public void run() 
	{
		
	}

}
