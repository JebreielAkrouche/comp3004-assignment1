package core;

import junit.framework.TestCase;

public class BlackJackTest extends TestCase {
	
	public void testDeck() {
		Deck d = new Deck();
		d.Generate();
		
		assertEquals(52, d.deckCards.size());
	}
	
	public void testShuffle() {
		Deck d1 = new Deck();
		d1.Generate();
		
		Deck d2 = new Deck();
		d2.Generate();
		d2.Shuffle();
		
		assertFalse(d1.deckCards.equals(d2.deckCards));
	}
	
	public void testHasRank() {
		GamePlayer gp1 = new GamePlayer();
		gp1.AddCard(new Card("C", "Q"));
		gp1.AddCard(new Card("D", "10"));
		
		assertTrue(gp1.hasRank("10"));
		
		GamePlayer gp2 = new GamePlayer();
		gp2.AddCard(new Card("S", "K"));
		gp2.AddCard(new Card("H", "2"));
		
		assertFalse(gp1.hasRank("A"));
	}
	
	public void testCount() {
		GamePlayer gp1 = new GamePlayer();
		gp1.AddCard(new Card("C", "Q"));
		gp1.AddCard(new Card("D", "10"));
		
		assertEquals(20, gp1.Count());
		
		GamePlayer gp2 = new GamePlayer();
		gp2.AddCard(new Card("S", "A"));
		gp2.AddCard(new Card("H", "5"));
		
		assertEquals(16, gp2.Count());
	}
}
