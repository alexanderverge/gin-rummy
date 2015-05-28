package ca.mcgill.cs.comp303.rummy.model;

import java.util.Random;

/**
 * AI: Random
 */
@SuppressWarnings("serial")
public class RandomPlayer extends Player
{

	private Random aRandom = new Random();

	/**
	 * Constructor.
	 * @param pName The player's name.
	 * @param pHand The player's hand.
	 */
	public RandomPlayer(String pName)
	{
		super(pName);
	}

	@Override
	public boolean draw(Card pCard)
	{
		return aRandom.nextBoolean();
	}

	@Override
	public Card discard()
	{
		//It is possible to discard a card drawn from the deck
		//provided that card is not null
		if(Math.random()<=0.1 && !(deckCard.isEmpty()))
		{
			Card aCard=deckCard.remove(0);
			return aCard;
		}
		else{
			Card aCard = aHand.getAllCards().get(aRandom.nextInt(aHand.size()));
			aHand.remove(aCard);
			//Add the deckCard if this ArrayList is not empty at this point.
			if(!(deckCard.isEmpty()))
			{
				aHand.add(deckCard.remove(0));
			}
			//Add the discardedCard to the Hand as this ArrayList can never
			//be discarded immediately.
			if(!(discardedCard.isEmpty()))
			{
				Card discardCard = discardedCard.remove(0);
				aHand.add(discardCard);
			}
			return aCard;
		}
	}

	@Override
	public boolean knock()
	{
		if (canKnock())
		{
			return aRandom.nextBoolean();
		}
		return false;
	}

}
