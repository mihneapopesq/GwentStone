package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles the command to retrieve Player One's win count.
 */
public final class GetPlayerOneWins {

    /**
     * Executes the command to get Player One's win count.
     *
     * @param objectMapper the object mapper for creating JSON nodes
     * @param output       the output array for storing results
     * @param wins         the number of wins for Player One
     */
    public void getPlayerOneWins(final ObjectMapper objectMapper,
                                 final ArrayNode output, final int wins) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerOneWins");
        resultNode.put("output", wins);
        output.add(resultNode);
    }
}
