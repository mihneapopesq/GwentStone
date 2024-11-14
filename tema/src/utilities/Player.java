package utilities;

import fileio.CardInput;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Player {

    Hero hero;
    Hand hand;
    private int heroAlreadyAttacked;
    int mana;
    private ArrayList<CardInput> deck;
    private int wins;
    private int gameEnded;


    public int getGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(int gameEnded) {
        this.gameEnded = gameEnded;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
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
        this.hero = new Hero(hero);

    }

    public CardInput getHero(){
        return hero.getCard();
    }

    public int getHeroAlreadyAttacked() {
        return heroAlreadyAttacked;
    }

    public void setHeroAlreadyAttacked(int heroAlreadyAttacked) {
        this.heroAlreadyAttacked = heroAlreadyAttacked;
    }

    public Player(ArrayList<CardInput> deck) {
        this.deck = new ArrayList<>();
        for (CardInput card : deck) {
            this.deck.add(copyCard(card));
        }
        this.hand = new Hand();
    }

    private CardInput copyCard(CardInput original) {
        CardInput copy = new CardInput();
        copy.setMana(original.getMana());
        copy.setAttackDamage(original.getAttackDamage());
        copy.setHealth(original.getHealth());
        copy.setDescription(original.getDescription());
        copy.setColors(new ArrayList<>(original.getColors()));
        copy.setName(original.getName());
        return copy;
    }



}