package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.testutils.AllCards;

/**
 * @author Joel Cheverie
 *
 *This is the TestCardSet class. It will be used to test the CardSet class.
 */
public class TestCardSet
{
	private CardSet aNonGroupCardSet;
	private CardSet aTooSmallCardSet;
	private CardSet aGroupCardSet;
	private CardSet aGroupFaceCardSet;
	private CardSet aRunCardSet;
	private CardSet aNonRunCardSet;
	private CardSet aBadSuitCardSet;
	private Set<Card> aNonGroupSet;
	private Card aCard;
	
	@Before
	public void setup()
	{
		initializeNonGroupSet();
		initializeGroupCardSet();
		initializeGroupFaceCardSet();
		initializeTooSmallCardSet();
		initializeRunCardSet();
		ArrayList<Card> aNonRunCardArrayList = initializeNonRunCardSet();
		initializeBadSuitCardSet(aNonRunCardArrayList);
	}
	
	//This tests the CardSet Constructor.
	@Test
	public void testConstructor()
	{
		for (Card c : aNonGroupCardSet)
		{
			assertTrue(aNonGroupSet.contains(c));
		}
		for (Card c : aNonGroupSet)
		{
			assertTrue(aNonGroupCardSet.contains(c));
		}
	}
	
	//This tests the Contains method.
	@Test
	public void testContains()
	{
		aCard = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
		assertEquals(true, aNonGroupCardSet.contains(aCard));
	}
	
	//This tests the size Method.
	@Test
	public void testSize()
	{
		int arraySize = aNonGroupSet.size();
		int aCardSetSize = aNonGroupCardSet.size();
		assertEquals(arraySize, aCardSetSize);
	}
	
	//This tests the isGroup method on a CardSet that is too small.
	@Test
	public void testTooSmallGroup()
	{
		assertEquals(false, aTooSmallCardSet.isGroup()); 
		
	}
	
	//This tests the isGroup method on a CardSet that is not a group.
	@Test
	public void testNotAGroup()
	{
		assertEquals(false, aNonGroupCardSet.isGroup()); 
	}
	
	//This tests the isGroup method on a CardSet that is a group.
	@Test
	public void testActualGroup()
	{
		assertEquals(true,aGroupCardSet.isGroup()); 
	}
	
	//This tests the isGroup method on a CardSet that is a group of face cards.
	@Test
	public void testActualGroupFaceCards()
	{
		assertEquals(true,aGroupFaceCardSet.isGroup()); 
	}
	
	//This tests the isRun method on a CardSet that is too small.
	@Test
	public void testTooSmallRun()
	{
		assertEquals(false, aTooSmallCardSet.isRun());
	}
	
	//This tests the isRun method on a CardSet that has the suit requirement, but is not a run.
	@Test
	public void testGoodSuitNonRun()
	{
		assertEquals(false, aNonRunCardSet.isRun());
	}
	
	//This tests the isRun method on a CardSet that does not have the suit requirement.
	@Test
	public void testBadSuit()
	{
		assertEquals(false, aBadSuitCardSet.isRun());
	}
	
	//This tests the isRun method on a CardSet that is in fact a Run.
	@Test
	public void testActualRun()
	{
		assertEquals(true, aRunCardSet.isRun());
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(aRunCardSet, aRunCardSet);
		assertFalse(aRunCardSet.equals(null));
		assertFalse(aRunCardSet.equals(new HashSet<Card>()));
		assertFalse(aRunCardSet.equals(aGroupCardSet));
		
		Set<Card> runCopy = new HashSet<Card>();
		runCopy.add(AllCards.C5C);
		runCopy.add(AllCards.C4C);
		runCopy.add(AllCards.C6C);
		runCopy.add(AllCards.C7C);
		ICardSet runCopySet = new CardSet(runCopy);
		
		assertEquals(aRunCardSet, runCopySet);
	}
	
	@Test
	public void testHashCode()
	{
		assertEquals(aRunCardSet.hashCode(), aRunCardSet.hashCode());
		assertFalse(aRunCardSet.hashCode() == aGroupCardSet.hashCode());
	}
	
	@Test
	public void testToString()
	{
		System.out.println(aRunCardSet.toString());
		assertEquals("CardSet [aCardSet=[FOUR of CLUBS, FIVE of CLUBS, SIX of CLUBS, SEVEN of CLUBS]]", aRunCardSet.toString());
	}
	
	// --- Private setup methods ---
	
	private void initializeBadSuitCardSet(ArrayList<Card> aNonRunCardArrayList)
	{
		aNonRunCardArrayList.add(AllCards.C2D);
		aBadSuitCardSet = new CardSet(aNonRunCardArrayList);
	}

	private ArrayList<Card> initializeNonRunCardSet()
	{
		ArrayList<Card> aNonRunCardArrayList = new ArrayList<Card>();
		aNonRunCardArrayList.add(AllCards.C5C);
		aNonRunCardArrayList.add(AllCards.CTC);
		aNonRunCardArrayList.add(AllCards.C6C);
		aNonRunCardArrayList.add(AllCards.C2C);
		aNonRunCardSet = new CardSet(aNonRunCardArrayList);
		return aNonRunCardArrayList;
	}

	private void initializeRunCardSet()
	{
		Set<Card> aRunCardArrayList = new HashSet<Card>();
		aRunCardArrayList.add(AllCards.C5C);
		aRunCardArrayList.add(AllCards.C4C);
		aRunCardArrayList.add(AllCards.C6C);
		aRunCardArrayList.add(AllCards.C7C);
		aRunCardSet= new CardSet(aRunCardArrayList);
	}

	private void initializeTooSmallCardSet()
	{
		Set<Card> aTooSmallArrayList = new HashSet<Card>();
		aTooSmallCardSet = new CardSet(aTooSmallArrayList);
	}

	private void initializeGroupCardSet()
	{
		Set<Card> aGroupCardArrayList = new HashSet<Card>(); 
		aGroupCardArrayList.add(AllCards.C5D);
		aGroupCardArrayList.add(AllCards.C5C);
		aGroupCardArrayList.add(AllCards.C5H);
		aGroupCardSet = new CardSet(aGroupCardArrayList);
	}
	
	private void initializeGroupFaceCardSet()
	{
		Set<Card> aGroupFaceCardArrayList = new HashSet<Card>(); 
		aGroupFaceCardArrayList.add(AllCards.CKD);
		aGroupFaceCardArrayList.add(AllCards.CKC);
		aGroupFaceCardArrayList.add(AllCards.CKH);
		aGroupFaceCardSet = new CardSet(aGroupFaceCardArrayList);
	}

	private void initializeNonGroupSet()
	{
		aNonGroupSet = new HashSet<Card>();
		aNonGroupSet.add(AllCards.C2D);
		aNonGroupSet.add(AllCards.CAS);
		aNonGroupSet.add(AllCards.CTH);
		aNonGroupSet.add(AllCards.CKC);
		aNonGroupCardSet = new CardSet(aNonGroupSet);
	}
}
