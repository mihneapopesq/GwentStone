package utilities.minions;

import utilities.Card;

/**
 * Represents a Minion card in the game
 */
public class Minion extends Card {

    /**
     * Constructs a Minion card based on an existing card
     *
     * @param card the card to base the minion on
     */
    public Minion(final Card card) {
        super(card);
    }

    /**
     * Executes the ability of this minion on the attacked card
     *
     * @param attackedCard the card being targeted by this minion's ability
     */
    public void cardAbility(final Minion attackedCard) {
        // Ability logic to be implemented in subclasses.
    }
}
