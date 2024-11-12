package utilities.minions;

import utilities.Card;

public class Disciple extends Minion {
    public Disciple(Card card) {
        super(card);
    }

    @Override
    public void cardAbility(Minion attackedCard) {
        int health = attackedCard.getCard().getHealth();
        attackedCard.getCard().setHealth(health + 2);
    }
}
