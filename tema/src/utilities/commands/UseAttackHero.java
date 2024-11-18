package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Card;
import utilities.Table;
import utilities.Player;

/**
 * A card, with given coordinates, attacks the enemy hero.
 */
public final class UseAttackHero {

    /**
     * Executes the "useAttackHero" command
     *
     * @param action      the action input
     * @param playerTurn  the current player's turn
     * @param table       the game table
     * @param objectMapper the object mapper for JSON nodes
     * @param output      the output array to store results
     * @param players     the players array
     */
    public void useAttackHero(final ActionsInput action, final int playerTurn, final Table table,
                              final ObjectMapper objectMapper, final ArrayNode output,
                              final Player[] players) {
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();

        if (attackerY >= table.getTable().get(attackerX).size()) {
            return;
        }

        Card attackerCard = table.getTable().get(attackerX).get(attackerY);

        if (attackerCard.getIsFrozen() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY,
                    "Attacker card is frozen.", output);
            return;
        }

        if (attackerCard.getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY,
                    "Attacker card has already attacked this turn.", output);
            return;
        }

        if (thereIsTank(playerTurn, table)) {
            makeErrorNodes(objectMapper, attackerX, attackerY,
                    "Attacked card is not of type 'Tank'.", output);
            return;
        }

        if (playerTurn == 0) {
            attackHero(players, 1, attackerCard, objectMapper, output);
        } else {
            attackHero(players, 0, attackerCard, objectMapper, output);
        }
    }

    private void attackHero(final Player[] players, final int targetPlayer, final Card attackerCard,
                            final ObjectMapper objectMapper, final ArrayNode output) {
        Player target = players[targetPlayer];
        target.getHero().setHealth(
                target.getHero().getHealth() - attackerCard.getCard().getAttackDamage()
        );
        attackerCard.setAlreadyAttacked(1);

        if (target.getHero().getHealth() <= 0) {
            ObjectNode gameEndedNode = objectMapper.createObjectNode();
            gameEndedNode.put("gameEnded",
                    targetPlayer == 0
                            ? "Player two killed the enemy hero."
                            : "Player one killed the enemy hero."
            );
            players[1 - targetPlayer].setGameEnded(1);
            output.add(gameEndedNode);
        }
    }

    private boolean thereIsTank(final int playerTurn, final Table table) {
        int row = (playerTurn == 0) ? 1 : 2; // Row to check for Tanks
        return table.getTable().get(row).stream().anyMatch(this::cardIsTank);
    }

    private boolean cardIsTank(final Card card) {
        String name = card.getCard().getName();
        return "Goliath".equals(name) || "Warden".equals(name);
    }

    private void makeErrorNodes(final ObjectMapper objectMapper, final int attackerX,
                                final int attackerY, final String message, final ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        errorNode.put("command", "useAttackHero");
        cardAttackerNode.put("x", attackerX);
        cardAttackerNode.put("y", attackerY);
        errorNode.set("cardAttacker", cardAttackerNode);
        errorNode.put("error", message);
        output.add(errorNode);
    }
}
