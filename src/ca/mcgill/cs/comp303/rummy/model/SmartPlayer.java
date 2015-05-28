package ca.mcgill.cs.comp303.rummy.model;

/**
 * This is the (smart) player that will use AI algorithms to make decisions.
 * NOTE: This code is rough and untested. 
 * It was developed to allow me to make progress on the AI.
 */
@SuppressWarnings("serial")
public class SmartPlayer extends Player
{

	private AIBrain aBrain = new BasicAI();

	/**
	 * Constructor.
	 * @param pName The player's name.
	 * @param pHand The player's hand.
	 */
	public SmartPlayer(String pName)
	{
		super(pName);
	}
	
	/**
	 * A constructor that allows you to specify the AI level.
	 * @param pName The player's name.
	 * @param pHand The player's hand.
	 * @param pLevel The AI algorithm level (difficulty).
	 */
	public SmartPlayer(String pName, int pLevel)
	{
		super(pName);
	}

	@Override
	public boolean draw(Card pCard)
	{
		return aBrain.drawFromDeck(aHand, pCard);
	}

	@Override
	public Card discard()
	{
		Card card = aBrain.calculateBestDiscard(aHand);
		aHand.remove(card);
		if(!(discardedCard.isEmpty()))
		{
			Card discardCard = discardedCard.remove(0);
			aHand.add(discardCard);
		}
		if(!(deckCard.isEmpty()))
		{
			Card discardCard = deckCard.remove(0);
			aHand.add(discardCard);
		}
		return card;
	}

	@Override
	public boolean knock()
	{
		if (canKnock())
		{
			return aBrain.shouldKnock(aHand);
		}
		return false;
	}

}
