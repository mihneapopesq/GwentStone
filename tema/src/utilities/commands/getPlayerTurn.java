package utilities.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

public class getPlayerTurn {

    private int playerTurn;

    public getPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void getPlayerTurn(ActionsInput action, ObjectNode actionNode, ArrayNode output) {
        actionNode.put("command", "getPlayerTurn");
        actionNode.put("output", playerTurn);
        output.add(actionNode);
    }
}