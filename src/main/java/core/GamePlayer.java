package core;

import java.util.*;

public class GamePlayer {
	int playerType; // 0 = dealer, 1 = player
	boolean current = false;
	List<Card> handCards = new ArrayList<Card>();
	
	public List<Card> GetHand () {
		return handCards;
	}
	
	public Card GetCard (int i) {
		if((i < handCards.size()) && (i >= 0))
			return handCards.get(i);
		return null;
	}
	
	public void AddCard (Card c) {
		if(c != null)
			handCards.add(c);
	}
	
	public void RemoveCard (int i) {
		if((i < handCards.size()) && (i >= 0))
			handCards.remove(i);
	}
	
	public void Hit (Deck deck) {
		handCards.add(deck.Deal());
	}
	
	public boolean hasRank(String rank) {
		if(handCards.size() > 0) {
			for(int i = 0; i < handCards.size(); i++) {
				if(handCards.get(i).getRank().equals(rank)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int Count () {
		int total = 0;
		int aceCount = 0;
		
		if(handCards.size() > 0) {
			for(int i = 0; i < handCards.size(); i++) {
				if(handCards.get(i).getRank().equals("J") ||
						handCards.get(i).getRank().equals("Q") ||
						handCards.get(i).getRank().equals("K")) {
					total += 10;
				} else if(handCards.get(i).getRank().equals("A")) {
					aceCount++;
				} else {
					total += Integer.parseInt(handCards.get(i).getRank());
				}
			}
			if(aceCount != 0) {
				for(int a = 0; a < aceCount; a++) {
					if(total <= 10) {
						total += 11;
					} else {
						total += 1;
					}
				}
			}
			return total;
		}
		return 0;
	}
}
