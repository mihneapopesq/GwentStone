package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

/**
 * Represents the hero Empress Thorina
 * Removes the card with the highest health from a row
 */
public final class EmpressThorina extends Hero {

    /**
     * Constructs a new Empress Thorina hero
     *
     * @param card the card representing this hero
     */
    public EmpressThorina(final CardInput card) {
        super(card);
    }

    /**
     * Applies the hero's special ability to remove the card with the highest health
     * from the specified row
     *
     * @param row the row of cards to analyze
     */
    @Override
    public void heroAbility(final ArrayList<Card> row) {
        int maxHealth = 0;
        int i = 0;
        int cardIdx = 0;

        for (final Card card : row) {
            if (card.getCard().getHealth() > maxHealth) {
                cardIdx = i;
                maxHealth = card.getCard().getHealth();
            }
            i++;
        }

        row.remove(cardIdx);
    }
}
