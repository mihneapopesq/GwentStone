package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Player;

public class getTotalGamesPlayed {
    public void getTotalGamesPlayed(ObjectMapper objectMapper, ArrayNode output, int gamesPlayed) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        errorNode.put("command", "getTotalGamesPlayed");
        errorNode.put("output", gamesPlayed);
        output.add(errorNode);
    }
}
