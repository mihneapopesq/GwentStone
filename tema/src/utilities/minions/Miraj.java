package utilities.minions;

import utilities.Card;

public class Miraj extends Minion {
    public Miraj(Card card) {
        super(card);
    }

    @Override
    public void cardAbility(Minion attackedCard) {
        int attackerHealth = this.getCard().getHealth();
        int targetHealth = attackedCard.getCard().getHealth();

        this.getCard().setHealth(targetHealth);
        attackedCard.getCard().setHealth(attackerHealth);
    }
}
