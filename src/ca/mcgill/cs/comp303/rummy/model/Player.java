package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract Player class.
 */
@SuppressWarnings("serial")
public abstract class Player implements Serializable
{

	protected Hand aHand;
	protected ArrayList<Card> deckCard = new ArrayList<Card>();
	protected ArrayList<Card> discardedCard = new ArrayList<Card>();
	private static int aMinToKnock = 11;
	private String aName;

	/**
	 * Constructor.
	 * @param pHand The player's hand.
	 * @param pName The player's name.
	 */
	public Player(String pName)
	{
		aName=pName;
	}
	
	/**
	 * Update the Player's name
	 * @param pName The Player's name.
	 */
	public void setName(String pName)
	{
		aName = pName;
	}

	/**
	 * @return the Player's name
	 * This getter will be used for testing and logging purposes.
	 * The implementation will be the same for all subclasses, so it
	 * Can be a final method.
	 */
	public final String getName()
	{
		return aName;
	}
	
	/**
	 * Set the players hand.
	 * @param pHand The player's hand.
	 */
	public void addHand(Hand pHand)
	{
		aHand = pHand;
	}
	
	/**
	 * Get the players hand.
	 * @return The players hand.
	 */
	public Hand getHand()
	{
		return aHand;
	}

	/**
	 * Get the score of the player's hand.
	 * @return the score of the hand.
	 */
	public int getScore()
	{
		return aHand.score();
	}
	
	/**
	 * Add a card to the player's hand.
	 * @param pCard The card to add.
	 * @param Deck If true the Card is added to the deckCard ArrayList. 
	 * Otherwise it is added to the discardedCard ArrayList.
	 */
	public void addCardToHand(Card pCard, Boolean Deck)
	{
		//this handles the 11th "temporary" drawn card problem.
		if(Deck){
			deckCard.add(pCard);
		}
		else{
			discardedCard.add(pCard);
		}
	}
	
	/**
	 * Get the Deck Cards.
	 * @return Deck Card list.
	 */
	public ArrayList<Card> getDeckCard(){
		return deckCard;
	}
	
	/**
	 * Get the discarded Cards.
	 * @return Discarded Card list.
	 */
	public ArrayList<Card> getDiscardedCard(){
		return discardedCard;
	}
	
	/**
	 * Check whether the player can knock.
	 * @return True if the player can knock.
	 */
	public boolean canKnock()
	{
		return getScore() < aMinToKnock;
	}

	/**
	 * Determine whether to draw from the deck or the last discarded card.
	 * @param pCard Last discarded card.
	 * @return If true then draw from the deck.
	 */
	public abstract boolean draw(Card pCard);

	/**
	 * Determine the card to discard.
	 * @return The card to discard.
	 */
	public abstract Card discard();


	/**
	 * Determine whether to knock.
	 * @return True if the player knocks.
	 */
	public abstract boolean knock();
	
}
