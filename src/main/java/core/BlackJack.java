package core;

import java.util.*;

public class BlackJack {
	
	static List<GamePlayer> userLi = new ArrayList<GamePlayer>();
	static List<GamePlayer> dealerLi = new ArrayList<GamePlayer>();
	static Deck deck = new Deck();
	
	public void PrintCards(int gameEnd) {
		System.out.print("D: ");
		for(int d = 0; d < dealerLi.size(); d++) {
			if(gameEnd == 0) {
				System.out.print(dealerLi.get(d).GetCard(0).getSuit() +
				dealerLi.get(d).GetCard(0).getRank());
			} else {
				System.out.print("(" + dealerLi.get(d).Count() + ") ");
				for(int i = 0; i < dealerLi.get(d).GetHand().size(); i++) {
					System.out.print(dealerLi.get(d).GetCard(i).getSuit() +
					dealerLi.get(d).GetCard(i).getRank() + " ");
				}
			}
			if(d != dealerLi.size()-1) {
				System.out.print("| ");
			}
		}
		System.out.println();
		System.out.print("P: ");
		for(int p = 0; p < userLi.size(); p++) {
			System.out.print("(" + userLi.get(p).Count() + ") ");
			for(int i = 0; i < userLi.get(p).GetHand().size(); i++) {
				System.out.print(userLi.get(p).GetCard(i).getSuit() +
				userLi.get(p).GetCard(i).getRank() + " ");
			}
			if(p != userLi.size()-1) {
				System.out.print("| ");
			}
		}
		System.out.println();
	}
}
