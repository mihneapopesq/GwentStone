package utilities.minions;

import utilities.Card;

/**
 * Represents the Disciple minion with its special healing ability
 * Heals the targeted card by 2 health points
 */
public class Disciple extends Minion {

    /**
     * Constructs a Disciple minion with the given card
     *
     * @param card the card to base the minion on
     */
    public Disciple(final Card card) {
        super(card);
    }

    /**
     * Executes Disciple's ability, healing the targeted card by 2 health points
     *
     * @param attackedCard the minion being healed
     */
    @Override
    public void cardAbility(final Minion attackedCard) {
        int health = attackedCard.getCard().getHealth();
        attackedCard.getCard().setHealth(health + 2);
    }
}
