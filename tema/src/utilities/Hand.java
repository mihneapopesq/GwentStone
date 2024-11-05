package utilities;

import java.util.ArrayList;

public class Hand {

    public ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void setCards(ArrayList<Card> hand) {
        this.cards = hand;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

}
