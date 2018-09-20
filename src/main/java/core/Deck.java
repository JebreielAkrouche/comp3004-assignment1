package core;

import java.util.*;
import java.util.Collections;

public class Deck {
	String[] suits = { "S", "C", "D", "H" };
	String[] ranks = { "A", "2", "3", "4", "5", "6",
						"7", "8", "9", "10", "J", "Q", "K"};
	
	List<Card> deckCards = new ArrayList<Card>();
	
	public void Generate() {
		deckCards.clear();
		for(int s = 0; s < suits.length; s++) {
			for(int r = 0; r < ranks.length; r++) {
				deckCards.add(new Card(suits[s], ranks[r]));
			}
		}
	}
	
	public void Shuffle() {
		Collections.shuffle(deckCards);
	}
	
	public Card Deal () {
		Card dealtCard = deckCards.get(0);
		deckCards.remove(0);
		return dealtCard;
	}
}
