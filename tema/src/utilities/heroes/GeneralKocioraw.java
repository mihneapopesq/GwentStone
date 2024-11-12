package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(CardInput card) {
        super(card);
    }

    @Override
    public void heroAbility(ArrayList<Card> row) {
       for(Card card : row) {
          card.getCard().setAttackDamage(card.getCard().getAttackDamage() + 1);
       }
    }
}
