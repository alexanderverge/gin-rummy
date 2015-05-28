package ca.mcgill.cs.comp303.rummy.model;

/**
 * AIBrain interface.
 */
public interface AIBrain
{

	/**
	 * Determine the most appropriate card to discard.
	 * @param pHand Current hand.
	 * @return Card to Discard.
	 */
	Card calculateBestDiscard(Hand pHand);
	
	/**
	 * Determine whether to draw form the deck or take the last discarded card.
	 * @param pHand Current hand.
	 * @param pCard Last discarded card.
	 * @return If true then draw from the deck.
	 */
	boolean drawFromDeck(Hand pHand, Card pCard);

	/**
	 * Determine if the player should knock.
	 * @param pHand The players current hand
	 * @return If true then the player should knock.
	 * @precondition pHand.size() > 10
	 * @precondition pHand.getScore() < 11
	 */
	boolean shouldKnock(Hand pHand);
	
}
