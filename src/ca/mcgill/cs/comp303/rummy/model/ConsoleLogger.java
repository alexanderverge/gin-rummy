package ca.mcgill.cs.comp303.rummy.model;

import java.util.logging.*;
import java.util.Observer;
import java.util.Observable;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;


/**
 * Logging Class
 */
public class ConsoleLogger implements Observer
{
	private static Logger aLogger = Logger.getLogger("console");
	private GameEngine aGameEngine;
	//Uncomment out the reference to the model below for the Pull implementation.
	//private Model aModel;
	
	//This is the constructor. It will need a pModel explicit pModel parameter if the pull
	//Model is used. Otherwise it is really just a wrapper for a logger.
	/**
	 * Constructor.
	 */
	public ConsoleLogger()
	{
		aGameEngine = GameEngine.getInstance();
		aGameEngine.addObserver(this);
	}
	
	//The following method is called from the model upon notification.
	//Note that it will be changed slightly depending on a Push/Pull implementation.
	@Override
	public void update(Observable pObvservable, Object pObject)
	{
		//The following line is an example of a Model state that can be logged.
    	//This will be modified to reflect the GameEngine being logged later. 
		ObserverFlag control=((ObserverFlag) pObject);
		Player currentPlayer= aGameEngine.getCurrentTurn();
		if(control==ObserverFlag.DRAW_DECK)
		{
			aLogger.log(Level.INFO, currentPlayer.getName()+ " drew from the deck.");
		}
		else if(control==ObserverFlag.DRAW_DISCARD)
		{
			aLogger.log(Level.INFO, currentPlayer.getName()+ " drew from discarded pile.");
		}
		else if (control==ObserverFlag.DISCARD_A_CARD)
		{
			aLogger.log(Level.INFO, currentPlayer.getName()+ " discarded " + aGameEngine.getCurrentDiscard().toString());
		}
		else if (control ==ObserverFlag.SAVE_ERROR)
		{
			aLogger.log(Level.INFO, "IOException: Save Error");
		}
		else if (control ==ObserverFlag.LOAD_ERROR)
		{
			aLogger.log(Level.INFO, "IOException: Load Error");
		}
		else if (control==ObserverFlag.KNOCKED)
		{
			aLogger.log(Level.INFO, currentPlayer.getName()+ " knocked.");
		}
//		aLogger.log(Level.INFO,String.valueOf(aModel.getNumber()));
	}
}

