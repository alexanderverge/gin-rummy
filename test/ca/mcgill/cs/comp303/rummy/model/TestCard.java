package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class TestCard
{
	private Card aCard;
	
	@Before
	public void setup()
	{
		aCard = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
	}

	@Test
	public void testConstructor()
	{
		assertEquals(Card.Rank.TWO, aCard.getRank());
		assertEquals(Card.Suit.DIAMONDS, aCard.getSuit());
	}
	
	@Test
	public void testToString()
	{
		assertEquals("TWO of DIAMONDS", aCard.toString());
	}
	
	@Test
	public void testValue()
	{
		assertEquals(2, aCard.value());
		Card queen = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
		assertEquals(10, queen.value());
	}
	
	@Test
	public void testGetOrdinal()
	{
		assertEquals(2, aCard.getOrdinal());
		Card queen = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
		assertEquals(12, queen.getOrdinal());
	}
	
	@Test
	public void testCompareToCardWithSameRankAndSuit()
	{
		Card other = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
		assertEquals(0, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithHigherRankAndLowerSuit()
	{
		Card other = new Card(Card.Rank.TEN, Card.Suit.CLUBS);
		assertEquals(-8, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithHigherRankAndHigherSuit()
	{
		Card other = new Card(Card.Rank.TEN, Card.Suit.HEARTS);
		assertEquals(-8, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithLowerRankAndHigherSuit()
	{
		Card other = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
		assertEquals(1, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithLowerRankAndLowerSuit()
	{
		Card other = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
		assertEquals(1, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithEqualRankAndLowerSuit()
	{
		Card other = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
		assertEquals(1, aCard.compareTo(other));
	}
	
	@Test
	public void testCompareToCardWithEqualRankAndHighererSuit()
	{
		Card other = new Card(Card.Rank.TWO, Card.Suit.SPADES);
		assertEquals(-2, aCard.compareTo(other));
	}
	
	@Test
	public void testEqualCardsAreEqual()
	{
		Card other = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
		assertTrue(aCard.equals(other));
	}
	
	@Test
	public void testDifferentCardsAreNotEqual()
	{
		Card other1 = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
		Card other2 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
		assertFalse(aCard.equals(other1));
		assertFalse(aCard.equals(other2));
	}
	
	@Test
	public void testHashCode()
	{
		assertEquals(14, aCard.hashCode());
	}
}
