package utilities;

import fileio.CardInput;
import java.util.ArrayList;

/**
 * Represents a hero card in the game with health and special abilities
 */
public class Hero extends Card {

    private int health;

    /**
     * Constructs a Hero object from the given CardInput
     *
     * @param card the CardInput object containing card data
     */
    public Hero(final CardInput card) {
        super(card);
        this.health = ConstantsConfig.HERO_INITIAL_HEALTH;
    }

    /**
     * Gets the health of the hero
     *
     * @return the current health of the hero
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the hero
     *
     * @param health the new health value
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Executes the hero's special ability on the given row of cards
     *
     * @param row the row of cards affected by the ability
     */
    public void heroAbility(final ArrayList<Card> row) {

    }
}
