package utilities.commands;

import fileio.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Player;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utilities.Card;


public class getCardsInHand {

    public void getCardsInHand(ActionsInput action, ObjectNode actionNode, Player player, ObjectMapper objectMapper, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getCardsInHand");
        actionNode.put("playerIdx", playerIdx);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (Card card : player.getHand().getCards()) {
            arrayNode.add(card.cardNode(card.getCard(),"card", objectMapper));
        }
        actionNode.set("output", arrayNode);
        output.add(actionNode);
    }

}
