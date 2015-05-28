package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Smart AI Class.
 */
@SuppressWarnings("serial")
public class BasicAI implements AIBrain, Serializable
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
	 * Create a list of cards in the Hand.
	 * @param pHand The hand.
	 * @return The list of cards.
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
		return value > cards.size(); // simple AI
	}

	@Override
	public boolean shouldKnock(Hand pHand)
	{
		return true;
	}

}
