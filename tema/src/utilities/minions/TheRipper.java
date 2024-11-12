package utilities.minions;

import utilities.Card;
import fileio.CardInput;

public class TheRipper extends Minion {
    public TheRipper(Card card) {
        super(card);
    }

    @Override
    public void cardAbility(Minion attackedCard) {
        int prevAttackDamage = attackedCard.getCard().getAttackDamage();
        attackedCard.getCard().setAttackDamage(prevAttackDamage - 2);
        if (attackedCard.getCard().getAttackDamage() < 2) {
            attackedCard.getCard().setAttackDamage(0);
        }
    }
}
