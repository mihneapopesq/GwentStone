package utilities.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.ActionsInput;
import fileio.CardInput;
import utilities.Player;
import utilities.Card;

public class getPlayerHero {
    private ObjectMapper objectMapper;

    public getPlayerHero() {
        this.objectMapper = new ObjectMapper();
    }

    public void getPlayerHero(ActionsInput action, ObjectNode actionNode, Player player, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerHero");
        actionNode.put("playerIdx", playerIdx);
        makeHeroArrayNode(player.getHero(), actionNode, output);
    }

    public void makeHeroArrayNode(CardInput hero, ObjectNode actionNode, ArrayNode output) {
        Card cardHelper = new Card(hero);
        ObjectNode heroNode = cardHelper.cardNode(hero, "hero", objectMapper);
        actionNode.set("output", heroNode);
        output.add(actionNode);
    }
}
