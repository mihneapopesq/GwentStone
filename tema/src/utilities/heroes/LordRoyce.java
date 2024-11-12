package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

public class LordRoyce extends Hero{
    public LordRoyce(CardInput card) {
        super(card);
    }

    @Override
    public void heroAbility(ArrayList<Card> row) {
        for(Card card : row) {
            card.setIsFrozen(1);
        }
    }
}
