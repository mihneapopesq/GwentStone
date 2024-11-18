package utilities.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.ActionsInput;
import fileio.CardInput;
import utilities.Player;
import utilities.Card;

/**
 * Command for retrieving a player's hero details
 */
public class GetPlayerHero {

    private final ObjectMapper objectMapper;

    /**
     * Default constructor initializes ObjectMapper
     */
    public GetPlayerHero() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Retrieves the hero details for a given player
     *
     * @param action     the action input containing player index
     * @param actionNode the node to store the action output
     * @param player     the player whose hero details are being retrieved
     * @param output     the output array node
     */
    public void getPlayerHero(final ActionsInput action, final ObjectNode actionNode,
                              final Player player, final ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerHero");
        actionNode.put("playerIdx", playerIdx);
        makeHeroArrayNode(player.getHero(), actionNode, output);
    }

    /**
     * Converts a hero's details into a JSON node and appends it to the output
     *
     * @param hero       the hero's card input details
     * @param actionNode the node to store the action output
     * @param output     the output array node
     */
    public void makeHeroArrayNode(final CardInput hero, final ObjectNode actionNode,
                                  final ArrayNode output) {
        Card cardHelper = new Card(hero);
        ObjectNode heroNode = cardHelper.cardNode(hero, "hero", objectMapper);
        actionNode.set("output", heroNode);
        output.add(actionNode);
    }
}
