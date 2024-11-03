package utilities;

import fileio.CardInput;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Player {

    CardInput hero;
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

    public void setHero(CardInput hero){
        this.hero = hero;
    }

    public CardInput getHero(){
        return hero;
    }

}