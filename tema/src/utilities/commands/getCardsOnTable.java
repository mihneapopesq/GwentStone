package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;


import utilities.Card;
import utilities.Table;

public class getCardsOnTable {
    public void getCardsOnTable(ActionsInput actionsInput, ObjectNode actionNode, Table table, ObjectMapper objectMapper, ArrayNode output) {
        ArrayNode rowsNode = objectMapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
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
