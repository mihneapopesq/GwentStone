package utilities.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles the command to retrieve the current player's turn
 */
public final class GetPlayerTurn {

    private final int playerTurn;

    /**
     * Constructs a new instance with the specified player turn
     *
     * @param playerTurn the current player's turn
     */
    public GetPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Executes the "getPlayerTurn" command
     *
     * @param actionNode the JSON node for this action
     * @param output     the output array to store the result
     */
    public void getPlayerTurn(final ObjectNode actionNode,
                              final ArrayNode output) {
        actionNode.put("command", "getPlayerTurn");
        actionNode.put("output", playerTurn);
        output.add(actionNode);
    }
}
