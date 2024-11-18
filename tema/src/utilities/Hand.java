package utilities;

import java.util.ArrayList;

/**
 * Represents a player's hand, containing a collection of cards
 */
public final class Hand {

    private ArrayList<Card> cards;

    /**
     * Constructs a new hand with an empty list of cards
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Sets the cards in the hand
     *
     * @param hand the new list of cards
     */
    public void setCards(final ArrayList<Card> hand) {
        this.cards = hand;
    }

    /**
     * Returns the cards in the hand
     *
     * @return the list of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}
