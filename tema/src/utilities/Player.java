package utilities;

import fileio.CardInput;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Player {

    CardInput hero;
    Hand hand;
    private ArrayList<CardInput> deck;

    public void addManaHero(int mana){
        hero.setMana(hero.getMana() + mana);
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

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