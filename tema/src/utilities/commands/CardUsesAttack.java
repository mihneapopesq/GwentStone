package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.ConstantsConfig;
import utilities.Table;
import utilities.Card;

/**
 * Handles the logic for a card attacking another card
 */
public final class CardUsesAttack {

    /**
     * Executes the attack command between cards
     *
     * @param actions      the action details
     * @param playerTurn   the current player's turn
     * @param table        the game table
     * @param objectMapper the object mapper
     * @param output       the output array for storing results
     */
    public void cardUsesAttack(final ActionsInput actions, final int playerTurn, final Table table,
                               final ObjectMapper objectMapper, final ArrayNode output) {
        int attackerX = actions.getCardAttacker().getX();
        int attackerY = actions.getCardAttacker().getY();
        int targetX = actions.getCardAttacked().getX();
        int targetY = actions.getCardAttacked().getY();

        // Check if attacker or target card positions are invalid
        if (attackerY >= table.getTable().get(attackerX).size()
                || targetY >= table.getTable().get(targetX).size()) {
            return;
        }

        // Check if the target belongs to the enemy
        if ((playerTurn == 0 && (targetX == 2 || targetX == ConstantsConfig.PLAYER_ONE_BACK_ROW))
                || (playerTurn == 1 && (targetX == 0 || targetX == 1))) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card does not belong to the enemy.", output);
            return;
        }

        // Check if the attacker card has already attacked
        if (table.getTable().get(attackerX).get(attackerY).getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacker card has already attacked this turn.", output);
            return;
        }

        // Check if the attacker card is frozen
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

        // Check if there is a Tank card blocking the attack
        if (thereIsTank(playerTurn, table) == 1
                && cardIsTank(table.getTable().get(targetX).get(targetY)) == 0) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY,
                    "Attacked card is not of type 'Tank'.", output);
            return;
        }

        // Perform the attack
        Card target = table.getTable().get(targetX).get(targetY);
        Card attacker = table.getTable().get(attackerX).get(attackerY);
        target.getCard().setHealth(target.getCard().getHealth() - attacker
                .getCard().getAttackDamage());
        attacker.setAlreadyAttacked(1);

        // Remove the target card if its health drops to 0
        if (target.getCard().getHealth() <= 0) {
            table.getTable().get(targetX).remove(targetY);
        }
    }

    /**
     * Checks if there is a Tank card on the table blocking the attack
     *
     * @param playerTurn the current player's turn
     * @param table      the game table
     * @return 1 if a Tank card exists, otherwise 0
     */
    private int thereIsTank(final int playerTurn, final Table table) {
        int row = playerTurn == 0 ? 1 : 2;
        for (Card card : table.getTable().get(row)) {
            if (cardIsTank(card) == 1) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Checks if the specified card is a Tank card
     *
     * @param card the card to check
     * @return 1 if the card is a Tank, otherwise 0
     */
    private int cardIsTank(final Card card) {
        String cardName = card.getCard().getName();
        return ("Goliath".equals(cardName) || "Warden".equals(cardName)) ? 1 : 0;
    }

    /**
     * Creates error nodes for invalid actions
     *
     * @param objectMapper the object mapper
     * @param attackerX    the x-coordinate of the attacker
     * @param attackerY    the y-coordinate of the attacker
     * @param targetX      the x-coordinate of the target
     * @param targetY      the y-coordinate of the target
     * @param errorMessage the error message to display
     * @param output       the output array for storing results
     */
    private void makeErrorNodes(final ObjectMapper objectMapper, final int attackerX,
                                final int attackerY, final int targetX, final int targetY,
                                final String errorMessage, final ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        ObjectNode cardAttackedNode = objectMapper.createObjectNode();
        errorNode.put("command", "cardUsesAttack");
        cardAttackerNode.put("x", attackerX);
        cardAttackerNode.put("y", attackerY);
        errorNode.set("cardAttacker", cardAttackerNode);
        cardAttackedNode.put("x", targetX);
        cardAttackedNode.put("y", targetY);
        errorNode.set("cardAttacked", cardAttackedNode);
        errorNode.put("error", errorMessage);
        output.add(errorNode);
    }
}
