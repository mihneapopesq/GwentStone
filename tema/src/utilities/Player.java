package utilities;

import fileio.CardInput;

import java.util.ArrayList;

/**
 * Represents a player in the game, containing hero, hand, deck
 */
public final class Player {

    private Hero hero;
    private Hand hand;
    private int heroAlreadyAttacked;
    private int mana;
    private final ArrayList<CardInput> deck;
    private int gameEnded;

    /**
     * Retrieves whether the game was ended or not
     *
     * @return the game ended status
     */
    public int getGameEnded() {
        return gameEnded;
    }

    /**
     * Sets whether the game was ended or not
     *
     * @param gameEnded the new game ended status
     */
    public void setGameEnded(final int gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * Returns the player's mana
     *
     * @return the current mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the player's mana
     *
     * @param mana the new mana value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Returns the player's hand
     *
     * @return the hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets the player's hand
     *
     * @param hand the new hand
     */
    public void setHand(final Hand hand) {
        this.hand = hand;
    }

    /**
     * Default Constructor
     * Initializes the player's deck
     */
    public Player() {
        this.deck = new ArrayList<>();
    }

    /**
     * Returns the player's deck
     *
     * @return the deck
     */
    public ArrayList<CardInput> getDeck() {
        return deck;
    }

    /**
     * Sets the player's hero
     *
     * @param hero the new hero
     */
    public void setHero(final CardInput hero) {
        this.hero = new Hero(hero);
    }

    /**
     * Returns the player's hero
     *
     * @return the hero card
     */
    public CardInput getHero() {
        return hero.getCard();
    }

    /**
     * Returns the hero attack status
     *
     * @return the hero already attacked status
     */
    public int getHeroAlreadyAttacked() {
        return heroAlreadyAttacked;
    }

    /**
     * Sets the hero attack status
     *
     * @param heroAlreadyAttacked the new status
     */
    public void setHeroAlreadyAttacked(final int heroAlreadyAttacked) {
        this.heroAlreadyAttacked = heroAlreadyAttacked;
    }

    /**
     * Constructor with a specified deck
     *
     * @param deck the initial deck
     */
    public Player(final ArrayList<CardInput> deck) {
        this.deck = new ArrayList<>();
        for (CardInput card : deck) {
            this.deck.add(copyCard(card));
        }
        this.hand = new Hand();
    }

    /**
     * Creates a deep copy of a card
     *
     * @param original the card to copy
     * @return the copied card
     */
    private CardInput copyCard(final CardInput original) {
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
