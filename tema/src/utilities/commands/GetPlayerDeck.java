package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import utilities.Player;
import utilities.Card;

import java.util.ArrayList;

/**
 * Handles the command to retrieve a player's deck
 */
public class GetPlayerDeck {

    /**
     * Converts a list of card inputs into an ArrayNode of card details
     *
     * @param cards         the list of card inputs
     * @param actionNode    the ObjectNode to append the output
     * @param objectMapper  the ObjectMapper
     * @param output        the ArrayNode to store the final result
     */
    public void makeCardArrayNode(final ArrayList<CardInput> cards, final ObjectNode actionNode,
                                  final ObjectMapper objectMapper, final ArrayNode output) {
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (CardInput card : cards) {
            Card cardHelper = new Card(card);
            arrayNode.add(cardHelper.cardNode(card, "card", objectMapper));
        }
        actionNode.set("output", arrayNode);
        output.add(actionNode);
    }

    /**
     * Retrieves a player's deck and formats it for output
     *
     * @param action        the input action containing the player index
     * @param actionNode    the ObjectNode to append the command details
     * @param player        the player whose deck is being retrieved
     * @param objectMapper  the ObjectMapper for JSON serialization
     * @param output        the ArrayNode to store the final result
     */
    public void getPlayerDeck(final ActionsInput action, final ObjectNode actionNode,
                              final Player player, final ObjectMapper objectMapper,
                              final ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerDeck");
        actionNode.put("playerIdx", playerIdx);
        makeCardArrayNode(player.getDeck(), actionNode, objectMapper, output);
    }
}
