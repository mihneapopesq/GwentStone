package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

public class EmpressThorina extends Hero {

    public EmpressThorina(CardInput card) {
        super(card);
    }

    @Override
    public void heroAbility(ArrayList<Card> row) {
        int max_health = 0;
        int i = 0, cardIdx = 0;
        for(Card card : row) {
            if(card.getCard().getHealth() > max_health) {
                cardIdx = i;
                max_health = card.getCard().getHealth();
            }
            i++;
        }
        row.remove(cardIdx);
    }
}
