package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.testutils.AllCards;

/**
 * @author Joel Cheverie
 *
 */
public class TestHand
{
	private Hand aEmptyHand;
	//aNormalHand will have 9 cards.
	private Hand aNormalHand;
	//aFullHand will have 10 cards.
	private Hand aFullHand;
	private HashSet<Card> groupHandSet;
	private HashSet<Card> runHandSet;
	
	@Before
	public void setup()
	{
		aEmptyHand=new Hand();
		aNormalHand = new Hand();
		initializeNormalHand();
		aFullHand = new Hand();
		initializeFullHand();
		initializeGroupSet();
		initializeRunSet();
	}
	
	//This tests the Hand class's constructor.
	@Test
	public void testConstructor()
	{
		assertTrue(aEmptyHand.getMatchedSets().isEmpty());
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
	}
	//This tests the add method of the Hand class under normal conditions.
	@Test
	public void testNormalAdd()
	{
		aNormalHand.add(AllCards.CAS);
		assertTrue(aNormalHand.getUnmatchedCards().size()==10);
		
	}
	
	//This tests that a HandException is returned if a Card is already in a Hand.
	@Test(expected = HandException.class)
	public void testAlreadyPresentAdd()
	{
		aNormalHand.add(AllCards.C2C);
	}
	
	//This tests that a HandException is returned if a Hand is full.
	@Test(expected = HandException.class)
	public void testFullHandAdd()
	{
		aFullHand.add(AllCards.C2C);
	}
	
	@Test
	public void testClear()
	{
		aEmptyHand.add(AllCards.C2C);
		aEmptyHand.add(AllCards.C4H);
		aEmptyHand.clear();
		assertTrue(aEmptyHand.getMatchedSets().isEmpty());
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
	}
		
	//This tests that the remove method for the Hand class works properly
	//if the Card is in the Hand.
	@Test
	public void testNormalRemove()
	{
		aEmptyHand.add(AllCards.C2C);
		HashSet<Card> singularHand = new HashSet<Card>();
		singularHand.add(AllCards.C2C);
		ICardSet singularSet = new CardSet(singularHand);
		aEmptyHand.getMatchedSets().add(singularSet);
		aEmptyHand.remove(AllCards.C2C);
		assertTrue(aEmptyHand.getMatchedSets().isEmpty());
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
		
	}
	
	//This tests that the remove method for the Hand class works properly
	//if the Card is not in the Hand.
	@Test
	public void testNonRemove()
	{
		aEmptyHand.add(AllCards.C2C);
		HashSet<Card> singularHand = new HashSet<Card>();
		singularHand.add(AllCards.C2C);
		ICardSet singularSet = new CardSet(singularHand);
		aEmptyHand.getMatchedSets().add(singularSet);
		aEmptyHand.remove(AllCards.C3C);
		aEmptyHand.getUnmatchedCards().remove(AllCards.C2C);
		assertTrue(aEmptyHand.getMatchedSets().size()>0);
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
		aEmptyHand.clear();
	}
	
	//This tests that the remove method for the Hand class 
	//modifies the matched sets properly.
	@Test
	public void testResetMatchedSetsRemove()
	{
		initializeDoubleMatchedSet();
		assertTrue(aEmptyHand.getMatchedSets().isEmpty());
		assertTrue(aEmptyHand.getUnmatchedCards().size()==1);
		//Reset the EmptyHand for other tests.
		aEmptyHand.clear();
	}
	
	//This tests that the size method calculates based on the size of both the
	//matched and unmatched sets.
	@Test
	public void testSize()
	{
		initializeSingleMatchedSet();
		assertEquals(3, aEmptyHand.size());
		aEmptyHand.clear();
	}
	
	//This tests that the contains method goes over the Matched sets of 
	//a Hand and returns true for cards being present
	@Test
	public void testMatchedContains()
	{
		initializeSingleMatchedSet();
		assertTrue(aEmptyHand.contains(AllCards.CQH));
		aEmptyHand.clear();
		
	}
	//This tests that the contains method goes over the matched set of
	// a Hand and returns false if cards are not present there or 
	//in the unMatched set.
	@Test
	public void testMatchedNoContains()
	{
		initializeSingleMatchedSet();
		assertFalse(aEmptyHand.contains(AllCards.CJS));
		aEmptyHand.clear();
	}
	
	
	//This tests that the score calculated by the score method only
	// calculates based on the unmatched set.
	@Test
	public void testScore()
	{
		initializeSingleMatchedSet();
		assertTrue(aEmptyHand.score()==11);
		aEmptyHand.clear();
	}
	
	//This tests the createGroup method with a Hand that is a group.
	@Test
	public void testCreateRealGroup()
	{
		aEmptyHand.add(AllCards.CJC);
		aEmptyHand.add(AllCards.CJD);
		aEmptyHand.add(AllCards.CJS);
		aEmptyHand.add(AllCards.CJH);
		aEmptyHand.createGroup(groupHandSet);
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
		aEmptyHand.clear();
	}
	
	//This tests the createGroup method with a Hand that is not a group.
	@Test(expected = HandException.class)
	public void testCreateNotAGroup()
	{
		HashSet<Card> nonGroupSet = new HashSet<Card>();
		nonGroupSet.add(AllCards.C2C);
		aEmptyHand.add(AllCards.C2C);;
		aEmptyHand.createGroup(nonGroupSet);
	}
	
	//This tests the createGroup method with a Hand that has a group
	//of cards that are already matched.
	@Test(expected = HandException.class)
	public void testCreateAlreadyMatchedGroup()
	{
		ICardSet groupHand = new CardSet(groupHandSet);
		aEmptyHand.getMatchedSets().add(groupHand);
		aEmptyHand.createGroup(groupHandSet);
	}
	
	//This tests the createRun method with a Hand that is a run.
	@Test
	public void testCreateRealRun()
	{
		aEmptyHand.add(AllCards.C8C);
		aEmptyHand.add(AllCards.C9C);
		aEmptyHand.add(AllCards.CTC);
		aEmptyHand.add(AllCards.CJC);
		aEmptyHand.createRun(runHandSet);
		assertTrue(aEmptyHand.getUnmatchedCards().isEmpty());
		aEmptyHand.clear();
	}
	
	//This tests the createRun method with a Hand that is not a run.
	@Test(expected = HandException.class)
	public void testCreateNotARun()
	{
		HashSet<Card> nonRunSet = new HashSet<Card>();
		nonRunSet.add(AllCards.C2C);
		aEmptyHand.add(AllCards.C2C);;
		aEmptyHand.createRun(nonRunSet);
	}
	
	//This tests the createRun method with a Hand that has a run
	//of cards that are already matched.
	@Test(expected = HandException.class)
	public void testCreateAlreadyMatchedRun()
	{
		ICardSet runHand = new CardSet(runHandSet);
		aEmptyHand.getMatchedSets().add(runHand);
		aEmptyHand.createRun(runHandSet);
	}

	private void initializeNormalHand()
	{
		aNormalHand.add(AllCards.C2C);
		aNormalHand.add(AllCards.C3C);
		aNormalHand.add(AllCards.CTD);
		aNormalHand.add(AllCards.CJS);
		aNormalHand.add(AllCards.CQH);
		aNormalHand.add(AllCards.C3S);
		aNormalHand.add(AllCards.CAD);
		aNormalHand.add(AllCards.C7S);
		aNormalHand.add(AllCards.C9H);
	}
	
	private void initializeFullHand()
	{
		aFullHand.add(AllCards.CAD);
		aFullHand.add(AllCards.CAS);
		aFullHand.add(AllCards.CTH);
		aFullHand.add(AllCards.C9D);
		aFullHand.add(AllCards.CJS);
		aFullHand.add(AllCards.C2H);
		aFullHand.add(AllCards.C4D);
		aFullHand.add(AllCards.C8C);
		aFullHand.add(AllCards.CKC);
		aFullHand.add(AllCards.C4S);
	}
	
	private void initializeSingleMatchedSet()
	{
		aEmptyHand.add(AllCards.CTD);
		aEmptyHand.add(AllCards.CAS);
		HashSet<Card> singularMatchedSet = new HashSet<Card>();
		singularMatchedSet.add(AllCards.CQH);
		ICardSet singleMatch=new CardSet(singularMatchedSet);
		aEmptyHand.getMatchedSets().add(singleMatch);
	}
	private void initializeDoubleMatchedSet()
	{
		aEmptyHand.add(AllCards.C2C);
		aEmptyHand.add(AllCards.C4S);
		HashSet<Card> doubleHand = new HashSet<Card>();
		doubleHand.add(AllCards.C2C);
		doubleHand.add(AllCards.C4S);
		ICardSet doubleSet = new CardSet(doubleHand);
		aEmptyHand.getMatchedSets().add(doubleSet);
		aEmptyHand.remove(AllCards.C2C);
	}
	
	private void initializeGroupSet()
	{
		groupHandSet = new HashSet<Card>();
		groupHandSet.add(AllCards.CJC);
		groupHandSet.add(AllCards.CJD);
		groupHandSet.add(AllCards.CJS);
		groupHandSet.add(AllCards.CJH);
		
	}
	
	private void initializeRunSet()
	{
		runHandSet = new HashSet<Card>();
		runHandSet.add(AllCards.C8C);
		runHandSet.add(AllCards.C9C);
		runHandSet.add(AllCards.CTC);
		runHandSet.add(AllCards.CJC);
	}
	
}
