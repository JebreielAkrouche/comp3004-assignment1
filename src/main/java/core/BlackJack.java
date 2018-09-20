package core;

import java.util.*;

public class BlackJack {
	
	static List<GamePlayer> userLi = new ArrayList<GamePlayer>();
	static List<GamePlayer> dealerLi = new ArrayList<GamePlayer>();
	static Deck deck = new Deck();
	static Scanner reader;
	
	public static void main(String[] args) {
		deck.Generate();
		deck.Shuffle();
		
		User user = new User();
		Dealer dealer = new Dealer();
		
		user.Hit(deck);
		user.Hit(deck);
		dealer.Hit(deck);
		dealer.Hit(deck);
		
		
		user.current = true;
		userLi.add(user);
		dealer.current = true;
		dealerLi.add(dealer);
		
		boolean isGameOver = false;
		String gameType = "";
		reader = new Scanner(System.in);
		//mainScan.useDelimiter("");
		while( !((gameType.equals("c")) || (gameType.equals("C")) ||
				(gameType.equals("f")) || (gameType.equals("F"))) ) {
			System.out.println("Select your input:  [C] for Console  [F] for File");
			gameType = reader.nextLine();
		}
		
		if(gameType.equals("c") || gameType.equals("C")) {
			BlackJack bj = new BlackJack();
			isGameOver = bj.ConsoleGame(true, 0);
			reader.close();
		} else if (gameType.equals("f") || gameType.equals("F")) {
			BlackJack bj = new BlackJack();
			isGameOver = bj.FileGame();
			reader.close();
		}
		else {
			reader.close();
			return;
		}
		
		if(isGameOver) {
			System.out.println("---RESULTS---");
			BlackJack bj = new BlackJack();
			bj.PrintCards(1);
			
			int winner = new BlackJack().checkWin(userLi, dealerLi);
			if(winner == 0)
				System.out.println("Dealer Wins!");
			else if(winner == 1)
				System.out.println("Player Wins!");
			else
				System.out.println("GAME IS INVALID!");
		} else {
			System.out.println("GAME IS INVALID!");
		}
	}
	
	
	
	// TURN ORDER:  false = dealer, true = player
	public boolean ConsoleGame(boolean playerTurn, int result) {
		BlackJack bj = new BlackJack();
		bj.PrintCards(0);
		while(result == 0) {
			if(playerTurn) {
				for(int u = 0; u < userLi.size(); u++) {
					String uOption = "";
					String dble = "";
					if(userLi.get(u).current) {
						if(userLi.get(u).Count() == 21) {
							result = 1;
							break;
						} else if (userLi.get(u).Count() > 21) {
							playerTurn = false;
							break;
						}
						//Take in input
						if(u == 0 && userLi.size() == 1 &&
								userLi.get(u).GetCard(0).getRank().equals(
										userLi.get(u).GetCard(1).getRank())) {
							System.out.println("Input an action:  [H] to Hit  [S] to Stand  [D] to Split");
							dble = reader.nextLine();
						}
						else {
							System.out.println("Input an action:  [H] to Hit  [S] to Stand");
							uOption = reader.nextLine();
						}
	
						if (dble.equals("d") || dble.equals("D")){
							User second = new User();
							second.AddCard(userLi.get(u).GetCard(1));
							userLi.get(u).RemoveCard(1);
							userLi.add(second);
						}
						else {
							if(uOption.equals("h") || uOption.equals("H")){
								userLi.get(u).Hit(deck);
							}
							else if(uOption.equals("s") || uOption.equals("S")){
								userLi.get(u).current = false;
								if(u != userLi.size()-1) {
									userLi.get(u+1).current = true;
								}
								if(u == userLi.size()-1) {
									playerTurn = false;
								}
							}
						}
					}
				}
			} else {
				for(int d = 0; d < dealerLi.size(); d++) {
					if(dealerLi.get(d).current) {
						// If splitting is good
						if(d == 0 && dealerLi.size() == 1 &&
								dealerLi.get(d).GetCard(0).getRank().equals(
										dealerLi.get(d).GetCard(1).getRank())) {
							if( (Integer.parseInt(dealerLi.get(d).GetCard(0).getRank()) < 9) ||
									dealerLi.get(d).GetCard(0).getRank().equals("A")) {
								Dealer second = new Dealer();
								second.AddCard(dealerLi.get(d).GetCard(1));
								dealerLi.get(d).RemoveCard(1);
								dealerLi.add(second);
							} else
								if( ((dealerLi.get(d).Count() == 17) && dealerLi.get(d).hasRank("A"))
								|| (dealerLi.get(d).Count() <= 16) ) {
									dealerLi.get(d).Hit(deck);
								} else {
									dealerLi.get(d).current = false;
									if(d != dealerLi.size()-1) {
										dealerLi.get(d+1).current = true;
									}
									if(d == dealerLi.size()-1) {
										result = 1;
									}
								}
						}
						// If hitting is good
						else if(((dealerLi.get(d).Count() == 17) && dealerLi.get(d).hasRank("A"))
								|| (dealerLi.get(d).Count() <= 16)) {
							dealerLi.get(d).Hit(deck);
						}
						// If standing is good
						else {
							dealerLi.get(d).current = false;
							if(d != dealerLi.size()-1) {
								dealerLi.get(d+1).current = true;
							}
							if(d == dealerLi.size()-1) {
								result = 1;
							}
						}
					}
				}
			}
			//
			if(result == 0) {
				BlackJack bjC = new BlackJack();
				return bjC.ConsoleGame(playerTurn, 0);
			}
		}
		return true;
	}
	
	
	
	public boolean FileGame() {
		User user = new User();
		Dealer dealer = new Dealer();
		userLi.clear();
		dealerLi.clear();
		
		//get input
		String input = "";
		System.out.println("Input your file data as follows:    A card (10 of Diamonds) looks like D10");
		System.out.println("The first four entries represents the player's and dealer's hand respectively");
		System.out.println("Comands H (Hit), D(Split) must be followed by a card entry");
		System.out.println("S (Stand) only should be followed by a card if a split will occur");
		System.out.println("A winner is determined if and only if your input is valid:");
		input = reader.nextLine();
		//parse input
		String[] parsedInput = input.split(" ");
		if(parsedInput.length < 4) {
			System.out.println("err: Less than four inputs.");
			return false;
		}
		if(parsedInput[parsedInput.length-1].length() == 1 && !parsedInput[parsedInput.length-1].equals("S")) {
			System.out.println("err: Ends in a non S.");
			return false;
		}
		for(int i = 0; i < parsedInput.length; i++) {
			if(parsedInput[i].length() > 1) {
				boolean vSuit = false;
				boolean vRank = false;
				for(int s = 0; s < deck.suits.length; s++) {
					for(int r = 0; r < deck.ranks.length; r++) {
						if(parsedInput[i].startsWith(deck.suits[s])) {
							vSuit = true;
						}
						if(parsedInput[i].endsWith(deck.ranks[r])) {
							vRank = true;
						}
					}
				}
				if(!vSuit || !vRank) {
					System.out.println("err: Invalid Suit or Rank.");
					return false;
				}	
			} else {
				if( !(parsedInput[i].equals("H") || parsedInput[i].equals("S") || parsedInput[i].equals("D")) ) {
					System.out.println("err: Invalid Command.");
					return false;
				}
			}
		}
		//evaluate input
		boolean standP = false;
		
		for(int k = 0; k < 4; k++) {
			String nxS = parsedInput[k].substring(0, 1);
			String nxR = parsedInput[k].substring(1);
			Card c = new Card(nxS, nxR);
			if(k > 1)
				dealer.AddCard(c);
			else
				user.AddCard(c);
		}
		userLi.add(user);
		dealerLi.add(dealer);
		for(int i = 0; i < parsedInput.length; i++) {
			if(parsedInput[i].length() == 1) {
				if(parsedInput[i].equals("H")) {
					if(!standP) {
						String nxS = parsedInput[i+1].substring(0, 1);
						String nxR = parsedInput[i+1].substring(1);
						Card c = new Card(nxS, nxR);
						user.AddCard(c);
					} else {
						if(userLi.size() > 1) {
							String nxS = parsedInput[i+1].substring(0, 1);
							String nxR = parsedInput[i+1].substring(1);
							Card c = new Card(nxS, nxR);
							userLi.get(1).AddCard(c);
						} else {
							System.out.println("err: Dealer Cannot Hit.");
							return false;
						}
					}
				} else if(parsedInput[i].equals("S")) {
					if(!standP) {
						standP = true;
						if(userLi.size() == 1) {
							//Dealer Split?
							if(dealer.GetHand().size() == 2 && userLi.size() == 1) {
								if(dealer.GetCard(0).getRank().equals(
									dealer.GetCard(1).getRank())) {
									Dealer d2 = new Dealer();
									d2.AddCard(dealer.GetCard(1));
									dealer.RemoveCard(1);
									dealerLi.add(d2);
								}
							}
							int offset = -1;
							//Dealer Stuff
							String[] remainder = Arrays.copyOfRange(parsedInput, i+1, parsedInput.length);
							String nxS;
							String nxR;
							for(int k = 0; k < remainder.length; k++) {
								offset++;
								if(((dealerLi.get(0).Count() == 17) && dealerLi.get(0).hasRank("A"))
									|| (dealerLi.get(0).Count() <= 16)) {
									if(remainder[k].length() > 1) {
										nxS = remainder[k].substring(0, 1);
										nxR = remainder[k].substring(1);
										Card c = new Card(nxS, nxR);
										dealerLi.get(0).AddCard(c);
									} else {
										break;
									}
								} else if(dealerLi.get(0).Count() == 21) {
									return true;
								} else {
									break;
								}
							}
							if(dealerLi.size() > 1) {
								String[] remainder2 = Arrays.copyOfRange(parsedInput, i+1+offset, parsedInput.length);
								for(int k = 0; k < remainder2.length; k++) {
									if(((dealerLi.get(1).Count() == 17) && dealerLi.get(1).hasRank("A"))
										|| (dealerLi.get(1).Count() <= 16)) {
										if(remainder2[k].length() > 1) {
											nxS = remainder2[k].substring(0, 1);
											nxR = remainder2[k].substring(1);
											Card c = new Card(nxS, nxR);
											dealerLi.get(1).AddCard(c);
										} else {
											break;
										}
									} else if(dealerLi.get(1).Count() == 21) {
										return true;
									} else {
										break;
									}
								}
							}
						} else {
							String nxS = parsedInput[i+1].substring(0, 1);
							String nxR = parsedInput[i+1].substring(1);
							Card c = new Card(nxS, nxR);
							userLi.get(1).AddCard(c);
						}
					} else {
						return true;
					}
				} else if(parsedInput[i].equals("D")) {
					if(!standP) {
						if(user.GetHand().size() == 2 && userLi.size() == 1) {
							User u2 = new User();
							u2.AddCard(user.GetCard(1));
							user.RemoveCard(1);
							userLi.add(u2);
							String nxS = parsedInput[i+1].substring(0, 1);
							String nxR = parsedInput[i+1].substring(1);
							Card c = new Card(nxS, nxR);
							user.AddCard(c);
						} else {
							System.out.println("err: Dealer Cannot Split.");
							return false;
						}
					} else {
						System.out.println("err: Other.");
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	// checks who won the game, 0 for the dealer, 1 for the player
	// -1 on error
	public int checkWin(List<GamePlayer> user, List<GamePlayer> dealer) {
		int topUser = 0;
		int topDealer = 0;
		for(int u = 0; u < user.size(); u++) {
			int currValue = user.get(u).Count();
			if((currValue > topUser) && (currValue <= 21)) {
				topUser = currValue;
			}
		}
		for(int d = 0; d < dealer.size(); d++) {
			int currValue = dealer.get(d).Count();
			if((currValue > topDealer) && (currValue <= 21)) {
				topDealer = currValue;
			}
		}
		
		// Check who wins
		if(topDealer >= topUser) {
			if(topDealer >= 0) {
				return 0;
			}
		}
		else {
			if(topUser >= 0) {
				return 1;
			}
		}
		
		return -1;
	}
	
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
