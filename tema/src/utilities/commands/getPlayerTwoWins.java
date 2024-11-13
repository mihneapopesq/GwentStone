package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Player;

public class getPlayerTwoWins {
    public void getPlayerTwoWins(ObjectMapper objectMapper, ArrayNode output, Player player) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        errorNode.put("command", "getPlayerTwoWins");
        errorNode.put("output", player.getWins());
        output.add(errorNode);
    }
}
