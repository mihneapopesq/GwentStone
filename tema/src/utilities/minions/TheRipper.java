package utilities.minions;

import utilities.Card;

/**
 * Represents the "The Ripper" minion
 * Reduces the attack damage of the target
 */
public class TheRipper extends Minion {

    /**
     * Constructs The Ripper minion with the given card
     *
     * @param card the card to base the minion on
     */
    public TheRipper(final Card card) {
        super(card);
    }

    /**
     * Executes The Ripper's ability, reducing the target's attack damage by 2
     *
     * @param attackedCard the minion being affected by the ability
     */
    @Override
    public void cardAbility(final Minion attackedCard) {
        int prevAttackDamage = attackedCard.getCard().getAttackDamage();
        attackedCard.getCard().setAttackDamage(prevAttackDamage - 2);

        if (attackedCard.getCard().getAttackDamage() < 0) {
            attackedCard.getCard().setAttackDamage(0);
        }
    }
}
