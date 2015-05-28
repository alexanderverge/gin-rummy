package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Smart AI Class.
 */
public class SmartAI implements AIBrain
{

	@Override
	public Card calculateBestDiscard(Hand pHand)
	{
		pHand.autoMatch();
		ArrayList<Card> cards = getCardsToConsider(pHand);
		
		Collections.sort(cards);
		return cards.get(cards.size()-1);
	}
	
	/**
	 * Obtain all the cards to consider.
	 * @param pHand The Hand.
	 * @return List of cards.
	 */
	private static ArrayList<Card> getCardsToConsider(Hand pHand)
	{
		ArrayList<Card> cards = new ArrayList<>();
		if (!pHand.getUnmatchedCards().isEmpty())
		{
			cards.addAll(pHand.getUnmatchedCards());
		}
		else
		{
			for (ICardSet cSet : pHand.getMatchedSets())
			{
				for (Card c : cSet)
				{
					cards.add(c);
				}
			}
		}
		
		return cards;
	}

	@Override
	public boolean drawFromDeck(Hand pHand, Card pCard)
	{
		ArrayList<Card> cards = (ArrayList<Card>) pHand.getAllCards();
		cards.add(pCard);
		Set<ICardSet> cardSets = (new SetMatcher()).matchSets(cards);
		int value = 0;
		for (ICardSet cardSet : cardSets)
		{
			value += cardSet.size();
		}
		return value > cards.size();
	}

	@Override
	public boolean shouldKnock(Hand pHand)
	{
		return true;
	}

}
