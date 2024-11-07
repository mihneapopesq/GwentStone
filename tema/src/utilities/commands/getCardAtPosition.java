package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Table;

import javax.swing.*;
import utilities.Card;
public class getCardAtPosition {
    public void getCardAtPosition(ActionsInput action, ObjectNode actionNode, Table Table, ObjectMapper objectMapper, ArrayNode output) {
        int x = action.getX();
        int y = action.getY();
        if(y >= Table.getTable().get(x).size()) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "getCardAtPosition");
            errorNode.put("output", "No card available at that position.");
            errorNode.put("x", x);
            errorNode.put("y", y);
            output.add(errorNode);
            return ;
        }
        Card card = Table.getTable().get(x).get(y); // presupun că `getTable().get(x).get(y)` returnează un obiect de tip `Card`
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
