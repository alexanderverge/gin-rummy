package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joel Cheverie
 *
 * This is the TestDeck class. It will be used to test the Deck class.
 */
public class TestDeck
{
	private Deck aDeck;
	
	@Before
	public void setup()
	{
		aDeck= new Deck();
	}
	
	//This tests the Deck Constructor. It also utilises the 
	// size method in the Deck class to do its test.
	@Test
	public void testConstructor()
	{
		assertEquals(52, aDeck.size());
	}
	
	//This tests the Deck Draw method. It uses the size
	//method of the Deck class to do its test as well.
	@Test
	public void testDraw()
	{
		aDeck.draw();
		assertEquals(51, aDeck.size());
	}
	
	//This tests the Deck Draw method on an empty deck.
	//This should violate the precondition of the draw method.
	@Test(expected=EmptyStackException.class)
	public void testEmptyDeck()
	{
		while (aDeck.size()>0)
		{
			aDeck.draw();
		}
		aDeck.draw();
	}
}
