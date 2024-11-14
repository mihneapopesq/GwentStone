package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Player;

public class getPlayerTwoWins {
    public void getPlayerTwoWins(ObjectMapper objectMapper, ArrayNode output, int wins) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        errorNode.put("command", "getPlayerTwoWins");
        errorNode.put("output", wins);
        output.add(errorNode);
    }
}
