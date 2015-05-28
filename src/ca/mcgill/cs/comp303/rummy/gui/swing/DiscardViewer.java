package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;

/**
 * A viewer for the discarded Card and the Deck.
 * The user interacts with this view to draw a card from either pile.
 */
@SuppressWarnings("serial")
public class DiscardViewer extends JPanel implements Observer
{
	private static ImageIcon aImageIcon;
	private JButton aDiscardPile;
	private JButton aDeckPile;
	private GameEngine aGameEngine;
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);
	
	/**
	 * Constructor.
	 * @param pImageIcon The discarded card to display.
	 */
	public DiscardViewer(ImageIcon pImageIcon)
	{
		super();
		setBackground(BACKGROUND_COLOR);
		aGameEngine = GameEngine.getInstance();
		aGameEngine.addObserver(this);
		aImageIcon = pImageIcon;
		aDeckPile = new JButton(CardImages.getBack());
		aDeckPile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				aGameEngine.drawDeck();
				disableDraw();
				refresh();
			}	
		});
		aDiscardPile = new JButton(aImageIcon);
		aDiscardPile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				aGameEngine.drawDiscard();
				disableDraw();
				refresh();
			}	
		});
		refresh();
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		ObserverFlag lObserverFlag = (ObserverFlag) arg;
		GameEngine lGameEngine = (GameEngine) o;
		if(lObserverFlag == ObserverFlag.DRAW_DISCARD
				|| lObserverFlag == ObserverFlag.NEW_GAME
				|| lObserverFlag == ObserverFlag.LOADED_GAME)
		{
			if(lGameEngine.getCurrentDiscard()!=null){
				aDiscardPile.setIcon(CardImages.getCard(lGameEngine.getCurrentDiscard()));
				aDiscardPile.setVisible(true);
			}
			else{
				aDiscardPile.setVisible(false);
			}
		}
		else if (lObserverFlag == ObserverFlag.DISCARD_A_CARD)
		{
			aDiscardPile.setIcon(CardImages.getCard(lGameEngine.getCurrentDiscard()));
			aDiscardPile.setVisible(true);
			if (aGameEngine.getCurrentTurn() == aGameEngine.getPlayer2())
			{
				enableDraw();
			}
		}		
		refresh();
	}
	
	/**
	 * Update the discarded card image displayed.
	 */
	private void refresh()
	{
		removeAll();
		add(aDiscardPile, BorderLayout.WEST);
		add(aDeckPile, BorderLayout.EAST);
		revalidate();
		repaint();
	}
	
	/**
	 * Enable the ability for the human player to draw a Card.
	 */
	public void enableDraw()
	{
		aDiscardPile.setEnabled(true);
		aDeckPile.setEnabled(true);
	}
	
	/**
	 * Disable the ability for the human player to draw a Card.
	 */
	public void disableDraw()
	{
		aDiscardPile.setEnabled(false);
		aDeckPile.setEnabled(false);
	}	
}
