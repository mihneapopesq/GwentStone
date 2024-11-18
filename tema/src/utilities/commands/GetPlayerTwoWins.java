package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles the command to retrieve Player Two's win count
 */
public final class GetPlayerTwoWins {

    /**
     * Executes the command to get Player Two's win count
     *
     * @param objectMapper the object mapper
     * @param output       the output array for storing results
     * @param wins         the number of wins for Player Two
     */
    public void getPlayerTwoWins(final ObjectMapper objectMapper,
                                 final ArrayNode output, final int wins) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerTwoWins");
        resultNode.put("output", wins);
        output.add(resultNode);
    }
}
