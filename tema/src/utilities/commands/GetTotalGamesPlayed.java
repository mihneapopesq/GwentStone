package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles the command to retrieve the total number of games played
 */
public final class GetTotalGamesPlayed {

    /**
     * Executes the command to retrieve the total number of games played
     *
     * @param objectMapper  the object mapper
     * @param output        the output array to store the result
     * @param gamesPlayed   the total number of games played
     */
    public void getTotalGamesPlayed(final ObjectMapper objectMapper,
                                    final ArrayNode output, final int gamesPlayed) {
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("command", "getTotalGamesPlayed");
        responseNode.put("output", gamesPlayed);
        output.add(responseNode);
    }
}
