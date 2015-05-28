package ca.mcgill.cs.comp303.rummy.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import ca.mcgill.cs.comp303.rummy.gui.swing.LoggerPanel;
import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Deck;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.Player;
import ca.mcgill.cs.comp303.rummy.model.RandomPlayer;
import ca.mcgill.cs.comp303.rummy.model.SmartPlayer;

/**
 * A Singleton class that manages the game state and state changing operations.
 */
public class GameEngine extends Observable implements Serializable
{
	private static final long serialVersionUID = 1314081761811586367L;
	private static GameEngine instance = new GameEngine();
	
	// p1 is human player, p2 is computer player
	private Player p1, p2, currentPlayer, otherPlayer, winner;
	private Deck aDeck;
	private ArrayList<Card> aDiscardedCards = new ArrayList<Card>();
	private ObserverFlag aObserverFlag;
	private int p1Score, p2Score;
	private static int aLevel = 5;
	
	public enum ObserverFlag
	{
		DRAW_DECK, DRAW_DISCARD, DISCARD_A_CARD, SAVE_ERROR, LOAD_ERROR, KNOCKED, NEW_GAME, LOADED_GAME, NAME_CHANGED
	}
	
	/**
	 * Constructor.
	 */
	private GameEngine()
	{
		p1Score = 0;
		p2Score = 0;
	}
	
	/**
	 * Get instance method.
	 * @return An reference to the GameEngine instance.
	 */
	public static GameEngine getInstance()
	{
		return instance;
	}
	
	/**
	 * Assign players.
	 * @param p1 The human player.
	 * @param p2 The opponent, computer player.
	 */
	public void setPlayers(Player p1, Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * Set the opponent difficulty.
	 * @param pLevel The opponent level from 0-10.
	 */
	private static void setLevel(int pLevel)
	{
		aLevel = (pLevel > 0) ? pLevel : 1;
	}
	
	/**
	 * Get player 1.
	 * @return The human Player
	 */
	public Player getPlayer1()
	{
		return p1;
	}

	/**
	 * Get player 2.
	 * @return The computer player.
	 */
	public Player getPlayer2()
	{
		return p2;
	}
	
	/**
	 * Get player 1's score.
	 * @return The human players score.
	 */
	public int getPlayer1Score()
	{
		return p1Score;
	}
	
	/**
	 * Get player 2's score.
	 * @return The computer player's score.
	 */
	public int getPlayer2Score()
	{
		return p2Score;
	}

	/**
	 * Get the winning player.
	 * @return The player that one.
	 */
	public Player getWinner()
	{
		return winner;
	}
	
	/**
	 * Get the human or computer depending on which is currently executing a move.
	 * @return The player currently executing a move.
	 */
	public Player getCurrentTurn() 
	{
		return currentPlayer;
	}

	/**
	 * Get a copy of the current Deck of Cards.
	 * @return the Deck of Cards.
	 */
	public Deck getDeck()
	{
		// the client applies a state changing operation.
		return aDeck.clone();
	}
	
	/**
	 * Get the current discarded card that is on the top of the stack.
	 * @return The most recently discarded card
	 */
	public Card getCurrentDiscard()
	{
		if(aDiscardedCards.isEmpty()) { return null; }
		return aDiscardedCards.get(aDiscardedCards.size()-1);
	}
	
	/**
	 * Update the Player's name.
	 * @param pName The Player's name.
	 */
	public void setName(String pName)
	{
		p1.setName(pName);
		aObserverFlag = ObserverFlag.NAME_CHANGED;
		setChanged();
		notifyObservers(aObserverFlag);
	}
	
	public void setOpponentName(String pName)
	{
		p2.setName(pName);
		aObserverFlag = ObserverFlag.NAME_CHANGED;
		setChanged();
		notifyObservers(aObserverFlag);
	}

	/**
	 * Game setup.
	 */
	public void newGame(int pLevel)
	{
		setLevel(pLevel);
		winner = null;
		
		aDeck = new Deck();
		aDeck.shuffle();
		Hand h1 = new Hand();
		Hand h2 = new Hand();
		
		for (int i = 0; i < 10; i++)
		{
			h1.add(aDeck.draw());
			h2.add(aDeck.draw());
		}
		
		p1.addHand(h1);
		p2.addHand(h2);
		
		//Initialize the discarded card.
		aDiscardedCards.add(aDeck.draw());
		
		//Initialize Player 1 (human) as first player.
		currentPlayer=p1;
		
		//Initialize Player 2 (computer) as the second player.
		otherPlayer=p2;
		
		aObserverFlag = ObserverFlag.NEW_GAME;
		setChanged();
		notifyObservers(aObserverFlag);
	}
	
	/**
	 * Draw a card from the deck.
	 */
	public void drawDeck(){
		Card toAdd = aDeck.draw();
		currentPlayer.addCardToHand(toAdd, true);
		aObserverFlag = ObserverFlag.DRAW_DECK;
		setChanged();
		notifyObservers(aObserverFlag);
	}

	/**
	 * Draw a card from the discard pile.
	 */
	public void drawDiscard(){
		Card toAdd = aDiscardedCards.remove(aDiscardedCards.size()-1);
		currentPlayer.addCardToHand(toAdd, false);
		aObserverFlag = ObserverFlag.DRAW_DISCARD;
		setChanged();
		notifyObservers(aObserverFlag);
	}

	/**
	 * Discard a card from a players hand.
	 * @param pCard The card to discard
	 */
	public void concreteDiscard(Card pCard){
		if(!currentPlayer.getDeckCard().isEmpty() && pCard.equals(currentPlayer.getDeckCard().get(0)))
		{
			currentPlayer.getDeckCard().remove(0);
		}
		else
		{
			currentPlayer.getHand().remove(pCard);
			if(!currentPlayer.getDeckCard().isEmpty())
			{
				currentPlayer.getHand().add(currentPlayer.getDeckCard().get(0));
				currentPlayer.getDeckCard().remove(0);
			}
			else if(!currentPlayer.getDiscardedCard().isEmpty())
			{
				currentPlayer.getHand().add(currentPlayer.getDiscardedCard().get(0));
				currentPlayer.getDiscardedCard().remove(0);
			}
		}
		aDiscardedCards.add(pCard);
		aObserverFlag = ObserverFlag.DISCARD_A_CARD;
		setChanged();
		notifyObservers(aObserverFlag);
		switchTurns();
		play();
	}
	
	/**
	 * React to a players knocking.
	 */
	public void endTurn(){
		if (currentPlayer.knock() || currentPlayer == p1)
		{
			int diff =  Math.abs(otherPlayer.getScore() - currentPlayer.getScore());
			
			// Gin
			if (currentPlayer.getScore() == 0)
			{
				winner = currentPlayer;
				if (currentPlayer == p1)
				{
					p1Score += diff + 20;
				}
				else
				{
					p2Score += diff + 20;
				}
			}
			else
			{
				if (diff > 0)
				{
					winner = currentPlayer;
					if (currentPlayer == p1)
					{
						p1Score += diff;
					}
					else
					{
						p2Score += diff;
					}
				}
				else
				{
					winner = otherPlayer;
					if (otherPlayer == p1)
					{
						p1Score += diff + 10;
					}
					else
					{
						p2Score += diff + 10;
					}
				}
			}
			aObserverFlag = ObserverFlag.KNOCKED;
			setChanged();
			notifyObservers(aObserverFlag);
			printScores();
		}
		else
		{
			aDiscardedCards.add(currentPlayer.discard());
			aObserverFlag = ObserverFlag.DISCARD_A_CARD;
			setChanged();
			notifyObservers(aObserverFlag);
		}

	}
	
	/**
	 * Implement a player's turn.
	 */
	public void play()
	{	
		if (aDeck.size() > 0)
		{	
			// Delay Draw.
			Timer lDrawTimer = new Timer();
			TimerTask lDrawTask = new TimerTask(){
				@Override
				public void run()
				{
					if (!currentPlayer.draw(aDiscardedCards.get(aDiscardedCards.size()-1)))
					{
						drawDeck();
					}
					else
					{
						drawDiscard();
					}
				}
			};
			double aTime = aLevel*500*Math.random();
			lDrawTimer.schedule(lDrawTask, (long) aTime);

			// Delay End Turn.
			Timer lEndTurnTimer = new Timer();
			TimerTask lEndTurnTask = new TimerTask()
			{
				@Override
				public void run()
				{
					endTurn();
					switchTurns();
				}
			};
			aTime = Math.max(aTime, aLevel*1000*Math.random());
			lEndTurnTimer.schedule(lEndTurnTask, (long) aTime);
		}
	}
	
	/**
	 * Switch the current player.
	 */
	private void switchTurns()
	{
		otherPlayer = currentPlayer;
		currentPlayer = (currentPlayer == p1 ? p2 : p1);
	}
	
	/**
	 * Output the current players names and scores to standard output.
	 */
	private void printScores()
	{
		System.out.println(p1.getName() + ":" + p1.getScore());
		System.out.println(p2.getName() + ":" + p2.getScore());
	}
	
	/**
	 * Method for saving the game state.
	 * @throws IOException
	 */
	public void saveGame() throws IOException
	{
		SavedGame lSavedGame = new SavedGame(this);
		FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try
	    {
		    fos = new FileOutputStream("savedGame.txt",false);
		    out = new ObjectOutputStream(fos);
		    out.writeObject(lSavedGame);
	    }
	    catch (IOException ex)
	    {
	    	ex.printStackTrace();
		}
	    finally
	    {
	    	if (out != null)
	    	{
	    		out.close();
	    	}
	    }
	}

	/**
	 * Load a saved game state.
	 * @throws IOException
	 */
	public void loadGame() throws IOException
	{
		FileInputStream door = null;
		ObjectInputStream reader = null; 
		try{ 
			door = new FileInputStream("savedGame.txt"); 
			reader = new ObjectInputStream(door); 
			SavedGame aGame = (SavedGame) reader.readObject();
			GameEngine loadedEngine = aGame.getGameEngine();
			aDeck = loadedEngine.aDeck;
			aObserverFlag = loadedEngine.aObserverFlag;
			p1 = loadedEngine.p1;
			p2 = loadedEngine.p2;
			currentPlayer = loadedEngine.getCurrentTurn();
			otherPlayer = loadedEngine.otherPlayer;
			p1Score = loadedEngine.p1Score;
			p2Score = loadedEngine.p2Score;
			aObserverFlag = ObserverFlag.LOADED_GAME;
			setChanged();
			notifyObservers(aObserverFlag);
		} catch (IOException e) {
         e.printStackTrace();
		} catch (ClassNotFoundException e) {
         e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				reader.close();
			}
		}
	}

	/**
	 * Method for testing.
	 * @param args Not used.
	 */
	public static void main(String[] args){
		//Hand fillerHand1= new Hand();
		//Hand fillerHand2= new Hand();
		Player p1 = new SmartPlayer("Joel");
		Player p2 = new RandomPlayer("Alex");
		GameEngine aGame = new GameEngine();
		aGame.setPlayers(p1, p2);
		//ConsoleLogger aConsoleLogger = new ConsoleLogger(aGame);
		
		//The next set of lines are used to build a LoggerPanel using the
		//Swing framework. They can be commented out if one does not want this 
		//Swing panel to be present.
		LoggerPanel aLoggerPanel = new LoggerPanel(aGame);
		JFrame frame = new JFrame("Logger Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(aLoggerPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//aGame.addObserver(aConsoleLogger);
		aGame.addObserver(aLoggerPanel);
		aGame.newGame(aLevel);
		try
		{
			aGame.saveGame();
		}
		catch (IOException ex) {
			aGame.aObserverFlag = ObserverFlag.SAVE_ERROR;
			aGame.notifyObservers(aGame.aObserverFlag.ordinal());
		}
		try
		{
			aGame.loadGame();
		}
		catch (IOException ex) {
			aGame.aObserverFlag = ObserverFlag.LOAD_ERROR;
			aGame.notifyObservers(aGame.aObserverFlag.ordinal());
		}
		aGame.play();
	}
}

/**
 * Class for saving the game state.
 */
class SavedGame implements Serializable
{
	private static final long serialVersionUID = 6051606478565015548L;
	private GameEngine aGameEngine;

	/**
	 * Constructor.
	 * @param pGameEngine The current instance of the GameEngine.
	 */
	public SavedGame(GameEngine pGameEngine)
	{
		aGameEngine = pGameEngine;
	}
	
	/**
	 * Get the saved instance of the GameEngine.
	 * @return The instance of GameEngine.
	 */
	public GameEngine getGameEngine(){
		return aGameEngine;
	}
}