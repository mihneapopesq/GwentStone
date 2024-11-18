package utilities.minions;

import utilities.Card;

/**
 * Represents The Cursed One minion with its special ability
 * Swaps the health and attack damage of the target
 */
public class TheCursedOne extends Minion {

    /**
     * Constructs The Cursed One minion with the given card
     *
     * @param card the card to base the minion on
     */
    public TheCursedOne(final Card card) {
        super(card);
    }

    /**
     * Executes The Cursed One's ability, swapping the health and attack damage
     * of the targeted card
     *
     * @param attackedCard the minion being attacked
     */
    @Override
    public void cardAbility(final Minion attackedCard) {
        int swapAux = attackedCard.getCard().getHealth();
        attackedCard.getCard().setHealth(attackedCard.getCard().getAttackDamage());
        attackedCard.getCard().setAttackDamage(swapAux);
    }
}
