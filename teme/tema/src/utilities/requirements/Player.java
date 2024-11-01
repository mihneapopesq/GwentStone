package utilities.requirements;

import fileio.CardInput;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Player {

    private ArrayList<CardInput> deck;

    public Player(){
        this.deck = new ArrayList<>();
    }

    public void setDeck(ArrayList<CardInput> deck) {
        this.deck = deck;
    }

    public ArrayList<CardInput> getDeck() {
        return deck;
    }

}
