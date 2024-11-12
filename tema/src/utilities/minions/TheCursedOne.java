package utilities.minions;

import utilities.Card;

public class TheCursedOne extends Minion {
    public TheCursedOne(Card card) {
        super(card);
    }

    @Override
    public void cardAbility(Minion attackedCard) {
        int swapAux = attackedCard.getCard().getHealth();
        attackedCard.getCard().setHealth(attackedCard.getCard().getAttackDamage());
        attackedCard.getCard().setAttackDamage(swapAux);
    }
}
