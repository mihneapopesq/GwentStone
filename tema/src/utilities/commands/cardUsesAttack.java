package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Player;
import utilities.Table;
import utilities.Card;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class cardUsesAttack {
    public void cardUsesAttack(ActionsInput actions, int playerTurn, Table Table, ObjectMapper objectMapper, ArrayNode output) {
        int attackerX = actions.getCardAttacker().getX();
        int attackerY = actions.getCardAttacker().getY();
        int targetX = actions.getCardAttacked().getX();
        int targetY = actions.getCardAttacked().getY();

        // nu exista carte aici
        if(targetY >= Table.getTable().get(targetX).size())
            return ;
        if(attackerY >= Table.getTable().get(attackerX).size())
            return ;


        if(playerTurn == 0 &&(targetX == 2 || targetX == 3)) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the enemy.", output);
            return ;
        } else if(playerTurn == 1 && (targetX == 0 || targetX == 1)) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the enemy.", output);
            return ;
        }

        if(Table.getTable().get(attackerX).get(attackerY).getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacker card has already attacked this turn.", output);
            return;
        }

        if(thereIsTank(playerTurn, Table) == 1 && cardIsTank(Table.getTable().get(targetX).get(targetY)) == 0) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card is not of type 'Tank'.", output);
            return;
        }

        Card target = Table.getTable().get(targetX).get(targetY);
        Card attacker = Table.getTable().get(attackerX).get(attackerY);
        target.getCard().setHealth(target.getCard().getHealth() - attacker.getCard().getAttackDamage());
        attacker.setAlreadyAttacked(1);
        if(target.getCard().getHealth() <= 0) {
            Table.getTable().get(targetX).remove(targetY);
        }
    }

    private int thereIsTank(int playerTurn, Table table) {
        int row;
        if(playerTurn == 0)
            row = 1;
        else
            row = 0;
        for(Card card : table.getTable().get(row)) {
            if(cardIsTank(card) == 1)
                return 1;
        }
        return 0;
    }

    private int cardIsTank(Card card) {
        if(card.getCard().getName().equals("Goliath") || card.getCard().getName().equals("Warden")) {
            return 1;
        }
        return 0;
    }

    public void makeErrorNodes(ObjectMapper objectMapper, int attackerX, int attackerY, int targetX, int targetY, String string, ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        ObjectNode cardAttackedNode = objectMapper.createObjectNode();
        errorNode.put("command", "cardUsesAttack");
        cardAttackerNode.put("x", attackerX);
        cardAttackerNode.put("y", attackerY);
        errorNode.set("cardAttacker", cardAttackerNode);
        cardAttackedNode.put("x", targetX);
        cardAttackedNode.put("y", targetY);
        errorNode.set("cardAttacked", cardAttackedNode);
        errorNode.put("error", string);
        output.add(errorNode);
    }

}
