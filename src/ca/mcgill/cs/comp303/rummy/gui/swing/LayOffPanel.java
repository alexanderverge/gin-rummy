package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;
import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.Player;

@SuppressWarnings("serial")
public class LayOffPanel extends JPanel implements Observer
{
	private static int BORDER = 20;
	private JLabel aLabel = new JLabel();
	private CardSelectionPanel aPanel1 = new CardSelectionPanel(11);
	private CardSelectionPanel aPanel2 = new CardSelectionPanel(11);
	
	/**
	 * Constructor.
	 */
	public LayOffPanel()
	{
		setLayout(new BorderLayout());
		aLabel.setBorder(new EmptyBorder(BORDER,BORDER,BORDER,BORDER));
		aLabel.setMinimumSize( new Dimension(100, 100));
		add(aPanel1, BorderLayout.NORTH);
		add(aLabel, BorderLayout.CENTER);
		add(aPanel2, BorderLayout.SOUTH);
		/* Do nothing until use KNOCK flag is received */
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		ObserverFlag lObserverFlag = (ObserverFlag) arg;
		GameEngine lGameEngine = (GameEngine) o;
		if (lObserverFlag == ObserverFlag.KNOCKED)
		{
			setLayOffPanel(lGameEngine);
		}		
	}

	/**
	 * Create LayOffPanel from GameEngine.
	 * @param pGameEngine
	 */
	private void setLayOffPanel(GameEngine pGameEngine)
	{	
		aPanel1.loadCards(getCards(pGameEngine,false));
		aPanel2.loadCards(getCards(pGameEngine,true));	
		scoreInformation(pGameEngine);
		revalidate();
		repaint();
	}
	
	/**
	 * Get the cards of the players hand.
	 * @param pGameEngine The GameEngine instance.
	 * @param p1 True if the cards should be players 1's.
	 * @return An array of cards ordered by matchings.
	 */
	private static Card[] getCards(GameEngine pGameEngine, boolean p1)
	{
		Player lPlayer;
		Card[] lCards = new Card[10];
		if (p1)
		{
			lPlayer = pGameEngine.getPlayer1();
		}
		else
		{
			lPlayer = pGameEngine.getPlayer2();
		}
		Hand lHand = lPlayer.getHand();
		Set<ICardSet> lMatched = lHand.getMatchedSets();
		Set<Card> lUnmatched = lHand.getUnmatchedCards();

		int i = 0;
		for (ICardSet lCardSet : lMatched)
		{
			for (Card lCard : lCardSet)
			{
				lCards[i++] = lCard;
			}
		}
		
		for (Card lCard : lUnmatched)
		{
			lCards[i++] = lCard;
		}
		
		return lCards;
	}
	
	/**
	 * Update the label attribute to show the game score information.
	 * @param pGameEngine The game engine instance.
	 */
	private void scoreInformation(GameEngine pGameEngine)
	{
		Player p1 = pGameEngine.getPlayer1();
		Player p2 = pGameEngine.getPlayer2();
		StringBuilder aMessage = new StringBuilder();
		aMessage.append("<html><body>");
		aMessage.append("Winner: " + pGameEngine.getWinner().getName());
		aMessage.append("<br>");
		aMessage.append(p1.getName() + ": " + p1.getScore());
		aMessage.append("<br>");
		aMessage.append(p2.getName() + ": " + p2.getScore());		
		aMessage.append("<body><html>");
		aLabel.setText(aMessage.toString());
	}
}
