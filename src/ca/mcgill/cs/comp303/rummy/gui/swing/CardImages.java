package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import ca.mcgill.cs.comp303.rummy.model.Card;

/**
 * A Flyweight class to store and manage images of the 52 cards and jokers.
 */
public final class CardImages 
{
	private static final String IMAGE_LOCATION = "";
	private static final String IMAGE_SUFFIX = ".gif";
	private static final String[] RANK_CODES = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
	private static final String[] SUIT_CODES = {"c", "d", "h", "s"};	
	
	private static Map<String, ImageIcon> aCards = new HashMap<String, ImageIcon>();
	
	/**
	 * Constructor.
	 */
	private CardImages(){}
	
	/**
	 * Return the image of a card.
	 * @param pCard The target card.
	 * @return An ImageIcon representing the chosen card.
	 */
	public static ImageIcon getCard( Card pCard )
	{
		return getCard( getCode( pCard ) );
	}
	
	/**
	 * Return an image of the back of a card.
	 * @return An ImageIcon representing the back of a card.
	 */
	public static ImageIcon getBack()
	{
		return getCard( "b" );
	}
	
	/**
	 * Return an image of the joker.
	 * @return An ImageIcon representing the joker.
	 */
	public static ImageIcon getJoker()
	{
		return getCard( "j" );
	}
	
	/**
	 * Generate a unique string code representing a card.
	 * @param pCard The card to generate a code for.
	 * @return The String code.
	 */
	private static String getCode( Card pCard )
	{
		return RANK_CODES[ pCard.getRank().ordinal() ] + SUIT_CODES[ pCard.getSuit().ordinal() ];		
	}
	
	/**
	 * Get the ImageIcon for a unique String code Card identifier.
	 * @param pCode The code representing the desired Card.
	 * @return The ImageIcon representing the desired Card.
	 */
	private static ImageIcon getCard( String pCode )
	{
		ImageIcon lIcon = (ImageIcon) aCards.get( pCode );
		if( lIcon == null )
		{
			lIcon = new ImageIcon(CardImages.class.getClassLoader().getResource( IMAGE_LOCATION + pCode + IMAGE_SUFFIX ));
			aCards.put( pCode, lIcon );
		}
		return lIcon;
	}
}
