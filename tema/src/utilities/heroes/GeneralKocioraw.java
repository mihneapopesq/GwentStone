package utilities.heroes;

import fileio.CardInput;
import utilities.Card;
import utilities.Hero;

import java.util.ArrayList;

/**
 * Represents the hero General Kocioraw
 * Increases the attack damage of all cards in a row by 1
 */
public final class GeneralKocioraw extends Hero {

    /**
     * Constructs a new General Kocioraw hero
     *
     * @param card the card representing this hero
     */
    public GeneralKocioraw(final CardInput card) {
        super(card);
    }

    /**
     * Applies the hero's special ability to increase the attack damage
     * of all cards in the specified row by 1
     *
     * @param row the row of cards to enhance
     */
    @Override
    public void heroAbility(final ArrayList<Card> row) {
        for (final Card card : row) {
            card.getCard().setAttackDamage(card.getCard().getAttackDamage() + 1);
        }
    }
}
