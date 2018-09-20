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
		
		GamePlayer gp3 = new GamePlayer();
		gp3.AddCard(new Card("C", "A"));
		gp3.AddCard(new Card("H", "A"));
		
		assertEquals(12, gp3.Count());
		
		GamePlayer gp4 = new GamePlayer();
		gp4.AddCard(new Card("S", "9"));
		gp4.AddCard(new Card("C", "10"));
		gp4.AddCard(new Card("S", "A"));
		gp4.AddCard(new Card("D", "A"));
		
		assertEquals(21, gp4.Count());
		
		GamePlayer gp5 = new GamePlayer();
		gp5.AddCard(new Card("S", "K"));
		gp5.AddCard(new Card("D", "Q"));
		gp5.AddCard(new Card("H", "J"));
		
		assertEquals(30, gp5.Count());
	}
	
	public void testInitCards () {
		System.out.println("public void testInitCards");
		Deck d = new Deck();
		d.Generate();
		d.Shuffle();
		
		User u = new User();
		Dealer e = new Dealer();
		
		u.AddCard(d.Deal());
		u.AddCard(d.Deal());
		
		e.AddCard(d.Deal());
		e.AddCard(d.Deal());
		
		BlackJack bj = new BlackJack();
		bj.userLi.clear();
		bj.userLi.add(u);
		bj.dealerLi.clear();
		bj.dealerLi.add(e);
		bj.PrintCards(0);
		System.out.println();
	}
	
	public void testInitBJ () {
		System.out.println("public void testInitBJ");
		User u1 = new User();
		u1.AddCard(new Card("S", "A"));
		u1.AddCard(new Card("S", "K"));
		
		Dealer e1 = new Dealer();
		e1.AddCard(new Card("C", "A"));
		e1.AddCard(new Card("D", "J"));
		
		BlackJack bj = new BlackJack();
		
		bj.userLi.clear();
		bj.userLi.add(u1);
		bj.dealerLi.clear();
		bj.dealerLi.add(e1);
		bj.PrintCards(1);
		assertEquals(0, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
		
		User u2 = new User();
		u2.AddCard(new Card("H", "Q"));
		u2.AddCard(new Card("C", "A"));
		
		Dealer e2 = new Dealer();
		e2.AddCard(new Card("C", "3"));
		e2.AddCard(new Card("S", "J"));
		
		BlackJack bj2 = new BlackJack();
		bj2.userLi.clear();
		bj2.userLi.add(u2);
		bj2.dealerLi.clear();
		bj2.dealerLi.add(e2);
		bj2.PrintCards(1);
		assertEquals(1, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
	}
	
	public void testBJ () {
		System.out.println("public void testBJ");
		User u1 = new User();
		u1.AddCard(new Card("C", "10"));
		u1.AddCard(new Card("S", "5"));
		u1.AddCard(new Card("H", "6"));
		
		Dealer e1 = new Dealer();
		e1.AddCard(new Card("D", "7"));
		e1.AddCard(new Card("C", "J"));
		
		BlackJack bj = new BlackJack();
		
		bj.userLi.clear();
		bj.userLi.add(u1);
		bj.dealerLi.clear();
		bj.dealerLi.add(e1);
		bj.PrintCards(1);
		assertEquals(1, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
		
		User u2 = new User();
		u2.AddCard(new Card("H", "K"));
		u2.AddCard(new Card("C", "8"));
		
		Dealer e2 = new Dealer();
		e2.AddCard(new Card("S", "A"));
		e2.AddCard(new Card("D", "J"));
		
		BlackJack bj2 = new BlackJack();
		bj2.userLi.clear();
		bj2.userLi.add(u2);
		bj2.dealerLi.clear();
		bj2.dealerLi.add(e2);
		bj2.PrintCards(1);
		assertEquals(0, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
	}
	
	public void testScoreCompare () {
		System.out.println("public void testScoreCompare");
		User u1 = new User();
		u1.AddCard(new Card("S", "10"));
		u1.AddCard(new Card("H", "9"));
		
		Dealer e1 = new Dealer();
		e1.AddCard(new Card("S", "7"));
		e1.AddCard(new Card("C", "K"));
		
		BlackJack bj = new BlackJack();
		
		bj.userLi.clear();
		bj.userLi.add(u1);
		bj.dealerLi.clear();
		bj.dealerLi.add(e1);
		bj.PrintCards(1);
		assertEquals(1, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
		
		User u2 = new User();
		u2.AddCard(new Card("S", "Q"));
		u2.AddCard(new Card("C", "8"));
		
		Dealer e2 = new Dealer();
		e2.AddCard(new Card("D", "9"));
		e2.AddCard(new Card("D", "K"));
		
		BlackJack bj2 = new BlackJack();
		bj2.userLi.clear();
		bj2.userLi.add(u2);
		bj2.dealerLi.clear();
		bj2.dealerLi.add(e2);
		bj2.PrintCards(1);
		assertEquals(0, bj.checkWin(bj.userLi, bj.dealerLi));
		System.out.println();
	}
	
	public void testInput () {
		BlackJack bj = new BlackJack();
		
	}
}
