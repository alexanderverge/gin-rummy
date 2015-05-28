package ca.mcgill.cs.comp303.rummy.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ca.mcgill.cs.comp303.rummy.model.Card;

/**
 * A singleton class that stores information useful for decision making game logic.
 * All or a subset of this information--depending on opponent difficulty--is accessible
 * to the AI players.
 */
public class AIEngine implements Observer
{
	private static AIEngine instance = new AIEngine();
	private GameEngine aGame = GameEngine.getInstance();
	private List<Card> aDiscardedCards = new ArrayList<Card>();
	
	/**
	 * Constructor.
	 */
	private AIEngine() 
	{
		aGame.addObserver(this);
	}
	
	/**
	 * Get instance method.
	 * @return A reference to the AIEngine instance.
	 */
	public static AIEngine getInstance()
	{
		return instance;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		Card lCard = aGame.getCurrentDiscard();
		if (!aDiscardedCards.contains(lCard))
		{
			aDiscardedCards.add(lCard);	
		}
	}
	
	/**
	 * Obtain information about the most recent discarded cards.
	 * @param n The number of cards requested.
	 * @return A list of the n most recently discarded cards.
	 */
	public List<Card> getRecentDiscardedCards(int n)
	{
		return aDiscardedCards.subList(aDiscardedCards.size() - n, aDiscardedCards.size());
	}
}
