package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Card;
import utilities.Table;
import utilities.Player;

public class useAttackHero {

    public void useAttackHero(ActionsInput action, int playerTurn, Table Table, ObjectMapper objectMapper, ArrayNode output, Player[] player) {
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();


        if(attackerY >= Table.getTable().get(attackerX).size()) {
            return;
        }

        if(Table.getTable().get(attackerX).get(attackerY).getIsFrozen() == 1) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            ObjectNode cardAttackerNode = objectMapper.createObjectNode();
            errorNode.put("command", "cardUsesAttack");
            cardAttackerNode.put("x", attackerX);
            cardAttackerNode.put("y", attackerY);
            errorNode.set("cardAttacker", cardAttackerNode);
            errorNode.put("error", "Attacker card is frozen.");
            output.add(errorNode);
            return;
        }


        if(Table.getTable().get(attackerX).get(attackerY).getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY, "Attacker card has already attacked this turn.", output);
            return;
        }

        if(thereIsTank(playerTurn,Table) == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY, "Attacked card is not of type 'Tank'.", output);
            return;
        }
        Card attackerCard = Table.getTable().get(attackerX).get(attackerY);
        if(playerTurn == 0) {
            player[1].getHero().setHealth(player[1].getHero().getHealth() - Table.getTable().get(attackerX).get(attackerY).getCard().getAttackDamage());
            attackerCard.setAlreadyAttacked(1);
            if(player[1].getHero().getHealth() <= 0) {
                ObjectNode gameEndedNode = objectMapper.createObjectNode();
                gameEndedNode.put("gameEnded", "Player one killed the enemy hero.");
                output.add(gameEndedNode);
            }
        } else {
            player[0].getHero().setHealth(player[0].getHero().getHealth() - Table.getTable().get(attackerX).get(attackerY).getCard().getAttackDamage());
            attackerCard.setAlreadyAttacked(1);
            if (player[0].getHero().getHealth() <= 0) {
                ObjectNode gameEndedNode = objectMapper.createObjectNode();
                gameEndedNode.put("gameEnded", "Player two killed the enemy hero.");
                output.add(gameEndedNode);
            }
        }
        player[playerTurn].setHeroAlreadyAttacked(1);
    }

    private int thereIsTank(int playerTurn, Table table) {
        int row;
        if(playerTurn == 0) // first player turn
            row = 1;        // verify second row of the table
        else
            row = 2;       //second player turn, verify third row of the table
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

    public void makeErrorNodes(ObjectMapper objectMapper, int attackerX, int attackerY, String string, ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        ObjectNode cardAttackedNode = objectMapper.createObjectNode();
        errorNode.put("command", "useAttackHero");
        cardAttackerNode.put("x", attackerX);
        cardAttackerNode.put("y", attackerY);
        errorNode.set("cardAttacker", cardAttackerNode);
        errorNode.put("error", string);
        output.add(errorNode);
    }

}
