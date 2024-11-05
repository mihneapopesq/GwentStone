package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import utilities.Player;
import utilities.Card;

import java.util.ArrayList;

public class getPlayerDeck {

    public void makeCardArrayNode(ArrayList<CardInput> cards, ObjectNode actionNode, ObjectMapper objectMapper, ArrayNode output) {
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (CardInput card : cards) {
            if (!card.getName().equals("Berserker") && !card.getName().equals("Sentinel")) {
                // Create a Card instance to use its cardNode method
                Card cardHelper = new Card(card);
                arrayNode.add(cardHelper.cardNode(card, "card", objectMapper));
            }
        }
        actionNode.set("output", arrayNode);
        output.add(actionNode);
    }

    public void getPlayerDeck(ActionsInput action, ObjectNode actionNode, Player player, ObjectMapper objectMapper, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerDeck");
        actionNode.put("playerIdx", playerIdx);
        makeCardArrayNode(player.getDeck(), actionNode, objectMapper, output);
    }
}
