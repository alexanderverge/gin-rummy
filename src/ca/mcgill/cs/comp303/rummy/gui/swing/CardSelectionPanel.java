package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;
import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.CompositeIcon;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.Player;
import ca.mcgill.cs.comp303.rummy.model.ShiftedIcon;

/**
 * An interactive JPanel and Observer that displays a hand of cards cards.
 * This class uses the composite and decorator patterns.
 */
@SuppressWarnings("serial")
public class CardSelectionPanel extends JPanel implements Observer
{
	private static final int H_SHIFT = 30;
	private static final int V_SHIFT = 20;
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36); // Card Table Green
	private static final int CARD_HEIGHT = CardImages.getBack().getIconHeight();
	private static final int CARD_WIDTH = CardImages.getBack().getIconWidth();
	private static final int PREFERRED_HEIGHT = CardImages.getBack().getIconHeight() + V_SHIFT;
	private static boolean isFaceDown = true;
	
	private Card[] aCards; 
	private Card selectedCard;
	private JLabel aLabel = new JLabel();
	private int aHovered = -1;
	private int aSelected = -1;
	private int currentHovered = -1;
	
	/**
	 * Creates an empty hand panel.
	 * @param pMaxCards The maximum number of cards that will be displayed.
	 */
	public CardSelectionPanel(int pMaxCards)
	{
		aCards = new Card[pMaxCards];
		setPreferredSize(new Dimension((pMaxCards -1) * H_SHIFT + CARD_WIDTH, PREFERRED_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		add(aLabel);
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent pEvent)
			{
				aHovered = getSelectedCardIndex(pEvent.getX(), pEvent.getY());
				paintCards();
			}
		});
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent pEvent)
			{
				int selection = getSelectedCardIndex(pEvent.getX(), pEvent.getY());
				selectedCard = aCards[selection];
				if( aSelected == selection )
				{
					aSelected = -1;
				}
				else
				{
					aSelected = selection;
				}
				if(currentHovered != -1){
					swapCards(selection,currentHovered);
					currentHovered = -1;
					aSelected = -1;
				}
				paintCards();
			}
		});
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		GameEngine lGameEngine = (GameEngine) o;
		ObserverFlag lObserverFlag = (ObserverFlag) arg;
		if (lObserverFlag == ObserverFlag.DISCARD_A_CARD)
		{
			aSelected = aHovered = currentHovered = -1;
		}
		
		loadCards(getCards(lGameEngine));		
	}

	/**
	 * Get the cards of the players hand.
	 * @param pGameEngine The GameEngine instance.
	 * @return An array of cards ordered by matchings.
	 */
	private static Card[] getCards(GameEngine pGameEngine)
	{
		Player lPlayer = pGameEngine.getPlayer1();
		Hand lHand = lPlayer.getHand();
		Card[] lCards = new Card[11];
		Set<ICardSet> lMatched = lHand.getMatchedSets();
		Set<Card> aUnmatched = lHand.getUnmatchedCards();

		int i = 0;
		for (ICardSet lCardSet : lMatched)
		{
			for (Card lCard : lCardSet)
			{
				lCards[i++] = lCard;
			}
		}
		
		for (Card lCard : aUnmatched)
		{
			lCards[i++] = lCard;
		}
		if(!lPlayer.getDiscardedCard().isEmpty())
		{
			lCards[i++] = lPlayer.getDiscardedCard().get(0);
		}
		if(!lPlayer.getDeckCard().isEmpty())
		{
			lCards[i++] = lPlayer.getDeckCard().get(0);
		}		
		return lCards;
	}
	
	/**
	 * Loads the cards and shows them on the panel.
	 * @param pCards The cards to load into the panel.
	 */
	public void loadCards(Card[] pCards)
	{
		Card[] lNewCards = (pCards == null) ? getCards() : pCards;
		ArrayList<Card> aAddList = new ArrayList<>();
		ArrayList<Card> aRemoveList = new ArrayList<>();
		ArrayList<Card> aCardList = new ArrayList<Card>(Arrays.asList(aCards));
		ArrayList<Card> pRemoveList = new ArrayList<>();
		ArrayList<Card> pCardList = new ArrayList<Card>(Arrays.asList(lNewCards));

		if (aCardList.isEmpty())
		{
			aCards = lNewCards.clone();
			paintCards();
			if (isFaceDown)
			{
				isFaceDown = false;
				paintCardsFaceDown();
			}
			return;
		}
		
		for (Card lCard : pCardList)
		{
			if (lCard == null || lCard.getRank() == null || lCard.getSuit() == null)
			{
				pRemoveList.add(lCard);
			}
		}		
		for (Card lCard : pRemoveList)
		{
			pCardList.remove(lCard);
		}
		
		// Determine Cards to add.
		for (Card lCard : pCardList)
		{
			if (!aCardList.contains(lCard))
			{
				aAddList.add(lCard);
			}
		}
		// Add Cards
		for (Card lCard : aAddList)
		{
			aCardList.add(lCard);
		}
		// Determine Cards to remove.
		for (Card lCard : aCardList)
		{
			if (!pCardList.contains(lCard))
			{
				aRemoveList.add(lCard);
			}
		}
		// Remove Cards
		for (Card lCard : aRemoveList)
		{
			aCardList.remove(lCard);
		}
		aCards = (Card[]) aCardList.toArray(new Card[aCardList.size()]);
		paintCards();
		if (isFaceDown)
		{
			isFaceDown = false;
			paintCardsFaceDown();
		}
	}
	
	/**
	 * Paint the cards face down during game setup.
	 */
	public void paintCardsFaceDown()
	{
		CompositeIcon lIcon = new CompositeIcon();
		for (int i = 0; i < 10; i++)
		{
			lIcon.addIcon(new ShiftedIcon( CardImages.getBack(), i * H_SHIFT, V_SHIFT));
		}
		aLabel.setIcon(lIcon);
		repaint();
	}
	
	/**
	 * Repaint the panel.
	 */
	private void paintCards()
	{
		CompositeIcon icon = new CompositeIcon();
		for( int i = 0; i < aCards.length; i++ )
		{
			if( aCards[i] == null )
			{
				continue;
			}
			int upShift = V_SHIFT;
			if( aHovered == i || aSelected == i)
			{
				upShift = 0;
			}
			icon.addIcon(new ShiftedIcon( CardImages.getCard(aCards[i]), i * H_SHIFT, upShift));
			if(aSelected == i)
			{
				currentHovered = i;
			}
		}
		aLabel.setIcon(icon);
		repaint();
	}
	
	/**
	 * Swap the location of two Cards in a Player's Hand.
	 * @param index1 The location of the first Card.
	 * @param index2 The location of the second Card.
	 */
	private void swapCards(int index1, int index2)
	{
		Card card1 = aCards[index1];
		aCards[index1] = aCards[index2];
		aCards[index2] = card1;
	}

	
	/**
	 * Get a copy of the current cards.
	 * @return The cards.
	 */
	public Card[] getCards()
	{
		return aCards.clone();
	}
	
	/**
	 * If there is a currently selected card then return the Card.
	 * @return The currently selected Card.
	 */
	public Card getSelectedCard()
	{
		return selectedCard;
	}
	
	/**
	 * Determine the selected Cards's index based on the location of an event.
	 * @param pX The horizontal location.
	 * @param pY The vertical location.
	 * @return The index of the selected card.
	 */
	private int getSelectedCardIndex(int pX, int pY)
	{
		if( pY < aLabel.getY() || pY >= aLabel.getY() + CARD_HEIGHT)
		{
			return -1;
		}
		int trueX = pX - aLabel.getX();
		if( trueX < 0 || trueX >= aLabel.getWidth())
		{
			return -1;
		}
		return Math.min(trueX / H_SHIFT, aCards.length-1);
	}
}