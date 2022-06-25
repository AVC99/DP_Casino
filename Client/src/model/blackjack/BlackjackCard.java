package model.blackjack;

public class BlackjackCard {
    private String suit; // Suit of the card (Clubs[0], Diamonds[1], Hearts[3], or Spades[4])
    private int rank; // Rank of the card (Ace[0], 2[1], 3[2], 4[3], 5[4], 6[5], 7[6], 8[7], 9[8], 10[9], Jack[10], Queen[11], or King[12])
    private int value; // Value of the card in blackjack (from 1 to 11)

    public BlackjackCard(String suit, int rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }
}
