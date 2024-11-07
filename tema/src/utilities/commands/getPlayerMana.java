package utilities.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Player;

public class getPlayerMana {

    public void getPlayerMana(ActionsInput action, ObjectNode actionNode, Player player, ArrayNode output) {
        actionNode.put("command", "getPlayerMana");
        actionNode.put("output", player.getMana());
        actionNode.put("playerIdx", action.getPlayerIdx());
        output.add(actionNode);
    }
}
