package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Card;
import utilities.ConstantsConfig;
import utilities.Table;
import utilities.minions.Disciple;
import utilities.minions.Miraj;
import utilities.minions.Minion;
import utilities.minions.TheCursedOne;
import utilities.minions.TheRipper;

/**
 * Handles the card ability actions during the game
 */
public final class CardUsesAbility {

    /**
     * Executes a card ability on a target card
     *
     * @param action        the action input containing card and target details
     * @param playerTurn    the current player's turn (0 or 1)
     * @param table         the game table containing all cards
     * @param objectMapper  the JSON object mapper for creating output
     * @param output        the array node to store the output results
     */
    public void cardUsesAbility(final ActionsInput action, final int playerTurn,
                                final Table table, final ObjectMapper objectMapper,
                                final ArrayNode output) {
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();
        int targetX = action.getCardAttacked().getX();
        int targetY = action.getCardAttacked().getY();

        if (table.getTable().get(attackerX).get(attackerY).getIsFrozen() == 1) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            ObjectNode cardAttackerNode = objectMapper.createObjectNode();
            errorNode.put("command", "cardUsesAttack");
            cardAttackerNode.put("x", attackerX);
            cardAttackerNode.put("y", attackerY);
            errorNode.set("cardAttacker", cardAttackerNode);
            errorNode.put("error", "Attacker card is frozen.");
            output.add(errorNode);
            return;
        }

        if (table.getTable().get(attackerX).get(attackerY).getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY,
                    targetX, targetY, "Attacker card has already attacked this turn.",
                    output);
            return;
        }
        if (playerTurn == 1 && (targetX == 2 || targetX == ConstantsConfig.PLAYER_ONE_BACK_ROW)
                && table.getTable().get(attackerX).get(attackerY)
                .getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card does not belong to the current player.", output);
            return;
        }
        if (playerTurn == 0 && (targetX == 1 || targetX == 0)
                && table.getTable().get(attackerX).get(attackerY)
                .getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card does not belong to the current player.", output);
            return;
        }
        if (playerTurn == 1 && (targetX == 0 || targetX == 1)
                && !table.getTable().get(attackerX).get(attackerY)
                .getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card does not belong to the enemy.", output);
            return;
        }
        if (playerTurn == 0 && (targetX == ConstantsConfig.PLAYER_ONE_BACK_ROW || targetX == 2)
                && !table.getTable().get(attackerX).get(attackerY)
                .getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card does not belong to the enemy.", output);
            return;
        }

        if (thereIsTank(playerTurn, table) == 1 && cardIsTank(table.getTable()
                .get(targetX).get(targetY)) == 0 && !table.getTable().get(attackerX)
                .get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card is not of type 'Tank'.", output);
            return;
        }

        Card attackerCard = table.getTable().get(attackerX).get(attackerY);
        Card attackedCard = table.getTable().get(targetX).get(targetY);
        if (attackerCard.getCard().getName().equals("Miraj")) {
            Miraj miraj = new Miraj(attackerCard);
            miraj.cardAbility(new Minion(attackedCard));
        } else if (attackerCard.getCard().getName().equals("The Ripper")) {
            TheRipper theRipper = new TheRipper(attackerCard);
            theRipper.cardAbility(new Minion(attackedCard));
        } else if (attackerCard.getCard().getName().equals("The Cursed One")) {
            TheCursedOne theCursedOne = new TheCursedOne(attackerCard);
            theCursedOne.cardAbility(new Minion(attackedCard));
        } else if (attackerCard.getCard().getName().equals("Disciple")) {
            Disciple disciple = new Disciple(attackerCard);
            disciple.cardAbility(new Minion(attackedCard));
        }

        if (attackedCard.getCard().getHealth() <= 0) {
            table.getTable().get(targetX).remove(targetY);
        }
        attackerCard.setAlreadyAttacked(1);
    }

    private int thereIsTank(final int playerTurn, final Table table) {
        int row;
        if (playerTurn == 0) { // first player turn
            row = 1; // first player turn, verify second row of the table
        } else {
            row = 2;  // second player turn, verify third row of the table
        }
        for (Card card : table.getTable().get(row)) {
            if (cardIsTank(card) == 1) {
                return 1;
            }
        }
        return 0;
    }

    private int cardIsTank(final Card card) {
        if (card.getCard().getName().equals("Goliath") || card.getCard()
                .getName().equals("Warden")) {
            return 1;
        }
        return 0;
    }

    /**
     * Creates and adds an error node to the specified output array
     *
     * @param objectMapper the JSON object mapper for creating nodes
     * @param attackerX the x-coordinate of the attacker
     * @param attackerY the y-coordinate of the attacker
     * @param targetX the x-coordinate of the target
     * @param targetY the y-coordinate of the target
     * @param string the error message
     * @param output the array node where the error node will be added
     */
    public void makeErrorNodes(final ObjectMapper objectMapper, final int attackerX,
                               final int attackerY, final int targetX, final int targetY,
                               final String string, final ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        ObjectNode cardAttackedNode = objectMapper.createObjectNode();
        errorNode.put("command", "cardUsesAbility");
        cardAttackerNode.put("x", attackerX);
        cardAttackerNode.put("y", attackerY);
        errorNode.set("cardAttacker", cardAttackerNode);
        cardAttackedNode.put("x", targetX);
        cardAttackedNode.put("y", targetY);
        errorNode.set("cardAttacked", cardAttackedNode);
        errorNode.put("error", string);
        output.add(errorNode);
    }
}
