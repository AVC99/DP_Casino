package model.blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDeck {
    private ArrayList<BlackjackCard> deck;

    public BlackjackDeck() {
        this.deck = this.createDeck();
    }

    private ArrayList<BlackjackCard> createDeck() {
        ArrayList<BlackjackCard> deck = new ArrayList<>();

        // Create 52 cards that form a deck
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                BlackjackCard card;
                String suit = "";

                switch (i) {
                    case 0 -> suit = "clubs";
                    case 1 -> suit = "diamonds";
                    case 2 -> suit = "hearts";
                    case 3 -> suit = "spades";
                }

                // Check if card is an Ace to assign value of 11 (Blackjack rules)
                if (j == 0) {
                    card = new BlackjackCard(suit, j, 11);
                }
                // Check if card is a Jack, Queen or King to assign value of 10 (Blackjack rules)
                else if (j >= 10) {
                    card = new BlackjackCard(suit, j, 10);
                }
                // Rest of cards
                else {
                    card = new BlackjackCard(suit, j, j + 1);
                }

                deck.add(card);
            }
        }

        return deck;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public BlackjackCard getCard(int i) {
        return deck.get(i);
    }

    public BlackjackCard removeCard(int i) {
        return deck.remove(i);
    }

    public ArrayList<BlackjackCard> getDeck() {
        return deck;
    }
}
