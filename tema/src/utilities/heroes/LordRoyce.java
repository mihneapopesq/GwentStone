package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

/**
 * Represents the hero Lord Royce
 * Freezes all cards in a row
 */
public final class LordRoyce extends Hero {

    /**
     * Constructs a new Lord Royce hero
     *
     * @param card the card representing this hero
     */
    public LordRoyce(final CardInput card) {
        super(card);
    }

    /**
     * Applies the hero's special ability to freeze cards in the given row
     *
     * @param row the row of cards to be frozen
     */
    @Override
    public void heroAbility(final ArrayList<Card> row) {
        for (final Card card : row) {
            card.setIsFrozen(1);
        }
    }
}
