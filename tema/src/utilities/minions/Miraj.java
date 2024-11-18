package utilities.minions;

import utilities.Card;

/**
 * Represents the Miraj minion with its special ability
 * Swaps the health values of the attacker and the attacked card
 */
public class Miraj extends Minion {

    /**
     * Constructs a Miraj minion with the given card
     *
     * @param card the card to base the minion on
     */
    public Miraj(final Card card) {
        super(card);
    }

    /**
     * Executes the ability of the Miraj minion, swapping health values
     * with the attacked card
     *
     * @param attackedCard the minion being attacked
     */
    @Override
    public void cardAbility(final Minion attackedCard) {
        int attackerHealth = this.getCard().getHealth();
        int targetHealth = attackedCard.getCard().getHealth();

        this.getCard().setHealth(targetHealth);
        attackedCard.getCard().setHealth(attackerHealth);
    }
}
