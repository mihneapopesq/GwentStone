package utilities.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utilities.Player;
import utilities.Card;
import fileio.ActionsInput;

/**
 * Handles the command to get cards in a player's hand
 */
public final class GetCardsInHand {

    /**
     * Executes the command to retrieve cards in the player's hand
     *
     * @param action        the action input containing player details
     * @param actionNode    the action node to populate with command data
     * @param player        the player whose hand is being queried
     * @param objectMapper  the object mapper
     * @param output        the output array to store the result
     */
    public void getCardsInHand(final ActionsInput action, final ObjectNode actionNode,
                               final Player player, final ObjectMapper objectMapper,
                               final ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getCardsInHand");
        actionNode.put("playerIdx", playerIdx);

        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (Card card : player.getHand().getCards()) {
            arrayNode.add(card.cardNode(card.getCard(), "card", objectMapper));
        }

        actionNode.set("output", arrayNode);
        output.add(actionNode);
    }
}
