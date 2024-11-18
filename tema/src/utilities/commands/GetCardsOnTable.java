package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Card;
import utilities.Table;

/**
 * Command for retrieving cards on the table
 */
public class GetCardsOnTable {

    /**
     * Retrieves the cards on the table and adds them to the output
     *
     * @param actionNode   the node to store the action output
     * @param table        the current game table
     * @param objectMapper the object mapper
     * @param output       the output array node
     */
    public void getCardsOnTable(final ObjectNode actionNode,
                                final Table table, final ObjectMapper objectMapper,
                                final ArrayNode output) {
        ArrayNode rowsNode = objectMapper.createArrayNode();
        final int totalRows = 4;

        for (int i = 0; i < totalRows; i++) {
            ArrayNode rowNode = objectMapper.createArrayNode();
            for (Card card : table.getTable().get(i)) {
                ObjectNode cardNode = card.cardNode(card.getCard(), "card", objectMapper);
                rowNode.add(cardNode);
            }
            rowsNode.add(rowNode);
        }

        actionNode.set("output", rowsNode);
        actionNode.put("command", "getCardsOnTable");
        output.add(actionNode);
    }
}
