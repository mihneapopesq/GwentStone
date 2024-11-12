package utilities.heroes;

import fileio.CardInput;
import utilities.Hero;
import utilities.Card;

import java.util.ArrayList;

public class KingMudface extends Hero{
    public KingMudface(CardInput card) {
        super(card);
    }

    @Override
    public void heroAbility(ArrayList<Card> row) {
        for(Card card : row) {
            card.getCard().setHealth(card.getCard().getHealth() + 1);
        }
    }
}
