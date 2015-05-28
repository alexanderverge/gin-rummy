package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.testutils.AllCards;

public class TestSetMatcher
{
	private List<Card> aHand;
	private SetMatcher aMatcher;
	
	@Before
	public void setup()
	{
		aHand = new ArrayList<Card>();
		aMatcher = new SetMatcher();
	}
	
	@Test
	public void testAutoMatch0()
	{
		aHand.add(AllCards.CAS);
		aHand.add(AllCards.CQC);
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C2D);
		aHand.add(AllCards.C7H);
		aHand.add(AllCards.CQH);
		aHand.add(AllCards.C6H);
		aHand.add(AllCards.C4D);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertTrue(solution.size() == 0);
	}

	@Test
	public void testAutoMatch1()
	{
		aHand.add(AllCards.C7D);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C7H);
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.C6D);
		aHand.add(AllCards.C9H);
		aHand.add(AllCards.CAH);
		aHand.add(AllCards.C4D);
		
		Set<Card> expectedCards = new HashSet<>();
		expectedCards.add(AllCards.C4C);
		expectedCards.add(AllCards.C4H);
		expectedCards.add(AllCards.C4D);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		
		assertEquals(1, solution.size());
		CardSet expected = new CardSet(expectedCards);		
		assertTrue(solution.contains(expected));
	}

	@Test
	public void testAutoMatch2()
	{
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.C9H);
		aHand.add(AllCards.CTS);
		aHand.add(AllCards.CJS);
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.CAC);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C2D);
		aHand.add(AllCards.C8D);
		aHand.add(AllCards.CJC);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertTrue(solution.size() == 0);
	}

	@Test
	public void testAutoMatch3()
	{
		aHand.add(AllCards.C8H);
		aHand.add(AllCards.CTD);
		aHand.add(AllCards.C9H);
		aHand.add(AllCards.CQC);
		aHand.add(AllCards.C6D);
		aHand.add(AllCards.CTC);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.CKD);
		aHand.add(AllCards.C7C);
		aHand.add(AllCards.CQD);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertTrue(solution.size() == 0);
	}

	@Test
	public void testAutoMatch4()
	{
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.CAH);
		aHand.add(AllCards.CTD);
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C5D);
		aHand.add(AllCards.C2H);
		aHand.add(AllCards.CJC);
		aHand.add(AllCards.C3H);
		aHand.add(AllCards.CAS);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		
		Set<Card> expectedCards = new HashSet<>();
		expectedCards.add(AllCards.CAH);
		expectedCards.add(AllCards.C2H);
		expectedCards.add(AllCards.C4H);
		expectedCards.add(AllCards.C3H);
		
		assertEquals(1, solution.size());
		CardSet expected = new CardSet(expectedCards);	
		assertTrue(solution.contains(expected));
	}

	@Test
	public void testAutoMatch5()
	{
		aHand.add(AllCards.C9H);
		aHand.add(AllCards.CKH);
		aHand.add(AllCards.C8H);
		aHand.add(AllCards.CAS);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C9D);
		aHand.add(AllCards.CJH);
		aHand.add(AllCards.CAD);
		aHand.add(AllCards.CQH);
		aHand.add(AllCards.CJC);
	}

	@Test
	public void testAutoMatch6()
	{
		aHand.add(AllCards.C5D);
		aHand.add(AllCards.CAD);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C3S);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C2S);
		aHand.add(AllCards.C7D);
		aHand.add(AllCards.C9H);
		aHand.add(AllCards.C3H);
		aHand.add(AllCards.CAS);
	}

	@Test
	public void testAutoMatch7()
	{
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.CJD);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C9D);
		aHand.add(AllCards.C7C);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C7H);
		aHand.add(AllCards.C2D);
		aHand.add(AllCards.CQH);
		aHand.add(AllCards.CAC);
	}

	@Test
	public void testAutoMatch8()
	{
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C7D);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.C2C);
		aHand.add(AllCards.CJD);
		aHand.add(AllCards.C8H);
		aHand.add(AllCards.C2H);
		aHand.add(AllCards.C8S);
	}

	@Test
	public void testAutoMatch9()
	{
		aHand.add(AllCards.CQC);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.C8D);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.CTH);
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.C9C);
		aHand.add(AllCards.CAS);
		aHand.add(AllCards.C2H);
	}

	@Test
	public void testAutoMatch10()
	{
		aHand.add(AllCards.C2H);
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.C7S);
		aHand.add(AllCards.C2D);
		aHand.add(AllCards.CTS);
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.C3H);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C2C);
	}

	@Test
	public void testAutoMatch11()
	{
		aHand.add(AllCards.CQD);
		aHand.add(AllCards.CKH);
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.CJC);
		aHand.add(AllCards.CAC);
		aHand.add(AllCards.C2S);
		aHand.add(AllCards.C8C);
		aHand.add(AllCards.C6H);
		aHand.add(AllCards.C7C);
		aHand.add(AllCards.C9C);
	}

	@Test
	public void testAutoMatch12()
	{
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.CAC);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.CTD);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C8D);
		aHand.add(AllCards.C9D);
		aHand.add(AllCards.CKC);
		aHand.add(AllCards.C5D);
	}

	@Test
	public void testAutoMatch13()
	{
		aHand.add(AllCards.CQC);
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C2H);
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.C7S);
		aHand.add(AllCards.C9D);
		aHand.add(AllCards.CTS);
		aHand.add(AllCards.CQD);
		aHand.add(AllCards.CKD);
	}

	@Test
	public void testAutoMatch14()
	{
		aHand.add(AllCards.C9S);
		aHand.add(AllCards.CKH);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C9C);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.C7S);
		aHand.add(AllCards.CQD);
		aHand.add(AllCards.CKS);
	}

	@Test
	public void testAutoMatch15()
	{
		aHand.add(AllCards.C2C);
		aHand.add(AllCards.CJD);
		aHand.add(AllCards.C6D);
		aHand.add(AllCards.CJC);
		aHand.add(AllCards.C4D);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.CKD);
	}

	@Test
	public void testAutoMatch16()
	{
		aHand.add(AllCards.C9S);
		aHand.add(AllCards.CAD);
		aHand.add(AllCards.CTH);
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.C7D);
		aHand.add(AllCards.CTC);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.CJS);
	}

	@Test
	public void testAutoMatch17()
	{
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C6D);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.CAD);
		aHand.add(AllCards.C5D);
		aHand.add(AllCards.C7C);
		aHand.add(AllCards.CJD);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C4H);
	}

	@Test
	public void testAutoMatch18()
	{
		aHand.add(AllCards.CKC);
		aHand.add(AllCards.C7C);
		aHand.add(AllCards.C8C);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.CQD);
		aHand.add(AllCards.C7H);
		aHand.add(AllCards.C7S);
		aHand.add(AllCards.CAH);
		aHand.add(AllCards.CTS);
		aHand.add(AllCards.CAD);
	}

	@Test
	public void testAutoMatch19()
	{
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.C7H);
		aHand.add(AllCards.CJS);
		aHand.add(AllCards.CTC);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.CJC);
		aHand.add(AllCards.C3C);
		aHand.add(AllCards.CTD);
		aHand.add(AllCards.C2S);
		aHand.add(AllCards.C8D);
	}
	
	@Test
	// 2-J to S
	public void test20()
	{
		aHand.add(AllCards.C2S);
		aHand.add(AllCards.C3S);
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.C7S);
		aHand.add(AllCards.C8S);
		aHand.add(AllCards.C9S);
		aHand.add(AllCards.CTS);
		aHand.add(AllCards.CJS);
		
		Set<Card> expectedCards = new HashSet<>();
		expectedCards.add(AllCards.C2S);
		expectedCards.add(AllCards.C3S);
		expectedCards.add(AllCards.C4S);
		expectedCards.add(AllCards.C5S);
		expectedCards.add(AllCards.C6S);
		expectedCards.add(AllCards.C7S);
		expectedCards.add(AllCards.C8S);
		expectedCards.add(AllCards.C9S);
		expectedCards.add(AllCards.CTS);
		expectedCards.add(AllCards.CJS);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertEquals(1, solution.size());
		CardSet expected = new CardSet(expectedCards);	
		
		//this.printMatchedSets();
		
		assertTrue(solution.contains(expected));
	}
	
	// 4 2's
	@Test
	public void test21()
	{
		aHand.add(AllCards.C2C);
		aHand.add(AllCards.C3H);
		aHand.add(AllCards.C2H);
		aHand.add(AllCards.C5D);
		aHand.add(AllCards.CTD);
		aHand.add(AllCards.C2D);
		aHand.add(AllCards.C2S);
		aHand.add(AllCards.CJS);
		aHand.add(AllCards.CQS);
		aHand.add(AllCards.CKH);
		
		Set<Card> expectedCards = new HashSet<>();
		expectedCards.add(AllCards.C2S);
		expectedCards.add(AllCards.C2C);
		expectedCards.add(AllCards.C2H);
		expectedCards.add(AllCards.C2D);
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);

		assertEquals(1, solution.size());
		CardSet expected = new CardSet(expectedCards);	
		assertTrue(solution.contains(expected));
	}
	
	// 4-6 of S, 4-6 of C, 4-7 of H
	@Test
	public void test22()
	{
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C6H);
		aHand.add(AllCards.C7H);
		
		Set<Card> spadeRun = new HashSet<>();
		spadeRun.add(AllCards.C4S);
		spadeRun.add(AllCards.C5S);
		spadeRun.add(AllCards.C6S);
		
		Set<Card> clubRun = new HashSet<>();
		clubRun.add(AllCards.C4C);
		clubRun.add(AllCards.C5C);
		clubRun.add(AllCards.C6C);
		
		Set<Card> heartRun = new HashSet<>();
		heartRun.add(AllCards.C4H);
		heartRun.add(AllCards.C5H);
		heartRun.add(AllCards.C6H);
		heartRun.add(AllCards.C7H);
		
		Set<ICardSet> expected = new HashSet<>();
		expected.add(new CardSet(spadeRun));
		expected.add(new CardSet(clubRun));
		expected.add(new CardSet(heartRun));
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);

		assertEquals(expected, solution);
	}

	// 3 4's, 3 5's, 4 6's
	@Test
	public void test23()
	{
		aHand.add(AllCards.C4S);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.C4C);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.C4H);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C6H);
		aHand.add(AllCards.C6D);
		
		Set<Card> fourGroup = new HashSet<>();
		fourGroup.add(AllCards.C4S);
		fourGroup.add(AllCards.C4C);
		fourGroup.add(AllCards.C4H);
		
		Set<Card> fiveGroup = new HashSet<>();
		fiveGroup.add(AllCards.C5S);
		fiveGroup.add(AllCards.C5C);
		fiveGroup.add(AllCards.C5H);
		
		Set<Card> sixGroup = new HashSet<>();
		sixGroup.add(AllCards.C6S);
		sixGroup.add(AllCards.C6C);
		sixGroup.add(AllCards.C6H);
		sixGroup.add(AllCards.C6D);
		
		Set<ICardSet> expected = new HashSet<>();
		expected.add(new CardSet(fourGroup));
		expected.add(new CardSet(fiveGroup));
		expected.add(new CardSet(sixGroup));
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertEquals(expected, solution);
	}
	
	// 3 K's, 3 5's, 4 6's
	@Test
	public void test24()
	{
		aHand.add(AllCards.CKS);
		aHand.add(AllCards.C5S);
		aHand.add(AllCards.C6S);
		aHand.add(AllCards.CKC);
		aHand.add(AllCards.C5C);
		aHand.add(AllCards.C6C);
		aHand.add(AllCards.CKH);
		aHand.add(AllCards.C5H);
		aHand.add(AllCards.C6H);
		aHand.add(AllCards.C6D);
		
		Set<Card> fourGroup = new HashSet<>();
		fourGroup.add(AllCards.CKS);
		fourGroup.add(AllCards.CKC);
		fourGroup.add(AllCards.CKH);
		
		Set<Card> fiveGroup = new HashSet<>();
		fiveGroup.add(AllCards.C5S);
		fiveGroup.add(AllCards.C5C);
		fiveGroup.add(AllCards.C5H);
		
		Set<Card> sixGroup = new HashSet<>();
		sixGroup.add(AllCards.C6S);
		sixGroup.add(AllCards.C6C);
		sixGroup.add(AllCards.C6H);
		sixGroup.add(AllCards.C6D);
		
		Set<ICardSet> expected = new HashSet<>();
		expected.add(new CardSet(fourGroup));
		expected.add(new CardSet(fiveGroup));
		expected.add(new CardSet(sixGroup));
		
		Set<ICardSet> solution = aMatcher.matchSets(aHand);
		assertEquals(expected, solution);
	}
	
	/* This test requires that getAllGroups() is public.
	// Test if the SetMatcher detects a single group of 3 kings.
	@Test
	public void testHighGroup()
	{
		aHand.add(AllCards.CKD);
		aHand.add(AllCards.CKC);
		aHand.add(AllCards.CKH);
		aMatcher.setCards(aHand);
		Set<ICardSet> lSets = aMatcher.getAllGroups();
		for (ICardSet lCardSet : lSets)
		{
			System.out.println(lCardSet.toString());
		}
		assertTrue(lSets.contains(new CardSet(aHand)));
	}
	*/
	
	/* This test requires that getAllRuns() is public.
	// Test if the SetMatcher detects a single group of 3 kings.
	@Test
	public void testHighRun()
	{
		aHand.add(AllCards.CJH);
		aHand.add(AllCards.CQH);
		aHand.add(AllCards.CKH);
		aMatcher.setCards(aHand);
		Set<ICardSet> lSets = aMatcher.getAllRuns();
		for (ICardSet lCardSet : lSets)
		{
			System.out.println(lCardSet.toString());
		}
		assertTrue(lSets.contains(new CardSet(aHand)));
	}
	*/
}
