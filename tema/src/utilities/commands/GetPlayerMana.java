package utilities.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Player;

/**
 * Handles the "getPlayerMana" command, retrieving the mana of a player
 */
public final class GetPlayerMana {

    /**
     * Executes the "getPlayerMana" command.
     *
     * @param action      the action input containing player index
     * @param actionNode  the JSON node for this action
     * @param player      the player whose mana is retrieved
     * @param output      the output array to store the result
     */
    public void getPlayerMana(final ActionsInput action, final ObjectNode actionNode,
                              final Player player, final ArrayNode output) {
        actionNode.put("command", "getPlayerMana");
        actionNode.put("output", player.getMana());
        actionNode.put("playerIdx", action.getPlayerIdx());
        output.add(actionNode);
    }
}
