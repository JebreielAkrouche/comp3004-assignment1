package core;

public class Card {
	String cardSuit = "";
	String cardRank = "";
	
	public Card (String s, String r) {
		cardSuit = s;
		cardRank = r;
	}
	
	public String getSuit () { return cardSuit; }
	public void setSuit (String s) { cardSuit = s; }
	public String getRank () { return cardRank; }
	public void setRank (String r) { cardRank = r; }
}
