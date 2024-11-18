package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Table;
import utilities.Card;

/**
 * Handles the "getCardAtPosition" command, retrieving details of a card at a specific position.
 */
public final class GetCardAtPosition {

    /**
     * Executes the "getCardAtPosition" command
     *
     * @param action       the action input containing position details
     * @param table        the table containing cards
     * @param objectMapper the JSON object mapper
     * @param output       the output array to store the result
     */
    public void getCardAtPosition(final ActionsInput action,
                                  final Table table, final ObjectMapper objectMapper,
                                  final ArrayNode output) {
        int x = action.getX();
        int y = action.getY();

        if (y >= table.getTable().get(x).size()) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "getCardAtPosition");
            errorNode.put("output", "No card available at that position.");
            errorNode.put("x", x);
            errorNode.put("y", y);
            output.add(errorNode);
            return;
        }

        Card card = table.getTable().get(x).get(y);
        ObjectNode cardDetails = objectMapper.createObjectNode();

        cardDetails.put("attackDamage", card.getCard().getAttackDamage());
        cardDetails.put("description", card.getCard().getDescription());
        cardDetails.put("health", card.getCard().getHealth());
        cardDetails.put("mana", card.getCard().getMana());
        cardDetails.put("name", card.getCard().getName());

        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : card.getCard().getColors()) {
            colorsArray.add(color);
        }
        cardDetails.set("colors", colorsArray);

        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("command", "getCardAtPosition");
        responseNode.set("output", cardDetails);
        responseNode.put("x", x);
        responseNode.put("y", y);
        output.add(responseNode);
    }
}
