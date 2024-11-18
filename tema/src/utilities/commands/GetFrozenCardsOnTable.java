package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Card;
import utilities.ConstantsConfig;
import utilities.Table;

/**
 * Command to retrieve all frozen cards on the table
 */
public class GetFrozenCardsOnTable {


    /**
     * Retrieves all frozen cards on the table
     *
     * @param table         the table containing the cards
     * @param objectMapper  the object mapper
     * @param output        the output array node
     * @param actionNode    the action node to store the command details
     */
    public void getFrozenCardsOnTable(final Table table, final ObjectMapper objectMapper,
                                      final ArrayNode output, final ObjectNode actionNode) {
        ArrayNode frozenCardsNode = objectMapper.createArrayNode();

        for (int i = 0; i < ConstantsConfig.MAX_ROWS; i++) {
            for (Card card : table.getTable().get(i)) {
                if (card.getIsFrozen() == 1) {
                    ObjectNode cardNode = card.cardNode(card.getCard(), "card", objectMapper);
                    frozenCardsNode.add(cardNode);
                }
            }
        }

        actionNode.set("output", frozenCardsNode);
        actionNode.put("command", "getFrozenCardsOnTable");
        output.add(actionNode);
    }
}
