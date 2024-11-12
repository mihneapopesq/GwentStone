package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Card;
import utilities.Table;

public class getFrozenCardsOnTable {
    public void getFrozenCardsOnTable(Table table, ObjectMapper objectMapper, ArrayNode output, ObjectNode actionNode) {
        ArrayNode frozenCardsNode = objectMapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            for (Card card : table.getTable().get(i)) {
                if (card.getIsFrozen() == 1) {
                    ObjectNode cardNode = card.cardNode(card.getCard(), "card", objectMapper);
                    frozenCardsNode.add(cardNode); // Add each frozen card directly to the output array
                }
            }
        }

        actionNode.set("output", frozenCardsNode);
        actionNode.put("command", "getFrozenCardsOnTable");
        output.add(actionNode);
    }
}

