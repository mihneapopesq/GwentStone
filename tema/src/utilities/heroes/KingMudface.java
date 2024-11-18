package utilities.heroes;

import fileio.CardInput;
import utilities.Hero;
import utilities.Card;

import java.util.ArrayList;

/**
 * Represents the hero King Mudface
 * Heals all cards in a row by 1 health point
 */
public final class KingMudface extends Hero {

    /**
     * Constructs a new King Mudface hero
     *
     * @param card the card representing this hero
     */
    public KingMudface(final CardInput card) {
        super(card);
    }

    /**
     * Applies the hero's special ability to heal cards in the given row
     *
     * @param row the row of cards to be healed
     */
    @Override
    public void heroAbility(final ArrayList<Card> row) {
        for (final Card card : row) {
            card.getCard().setHealth(card.getCard().getHealth() + 1);
        }
    }
}
