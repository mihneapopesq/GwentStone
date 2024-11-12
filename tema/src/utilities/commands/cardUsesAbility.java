package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Card;
import utilities.Table;
import utilities.minions.*;


public class cardUsesAbility {
    public void cardUsesAbility(ActionsInput action, int playerTurn, Table Table, ObjectMapper objectMapper, ArrayNode output) {
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();
        int targetX = action.getCardAttacked().getX();
        int targetY = action.getCardAttacked().getY();

//        if (targetY >= Table.getTable().get(targetX).size()) {
//            return;
//        }
//        if (attackerY >= Table.getTable().get(attackerX).size()) {
//            return;
//        }

        // todo verify if frozen


        if(Table.getTable().get(attackerX).get(attackerY).getAlreadyAttacked() == 1) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacker card has already attacked this turn.", output);
            return;
        }
        if(playerTurn == 1 && (targetX == 2 || targetX == 3) && Table.getTable().get(attackerX).get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the current player.", output);
            return;
        }
        if(playerTurn == 0 && (targetX == 1 || targetX == 0) && Table.getTable().get(attackerX).get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the current player.", output);
            return;
        }
        if(playerTurn == 1 && (targetX == 0 || targetX == 1) && !Table.getTable().get(attackerX).get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the enemy.", output);
            return;
        }
        if(playerTurn == 0 && (targetX == 3 || targetX == 2) && !Table.getTable().get(attackerX).get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card does not belong to the enemy.", output);
            return;
        }


        if(thereIsTank(playerTurn,Table) == 1 && cardIsTank(Table.getTable().get(targetX).get(targetY)) == 0 && !Table.getTable().get(attackerX).get(attackerY).getCard().getName().equals("Disciple")) {
            makeErrorNodes(objectMapper, attackerX, attackerY, targetX, targetY, "Attacked card is not of type 'Tank'.", output);
            return;
        }







        Card attackerCard = Table.getTable().get(attackerX).get(attackerY);
        Card attackedCard = Table.getTable().get(targetX).get(targetY);
        if(attackerCard.getCard().getName().equals("Miraj")) {
            Miraj miraj = new Miraj(attackerCard);
            miraj.cardAbility(new Minion(attackedCard));
        } else if(attackerCard.getCard().getName().equals("The Ripper")) {
            TheRipper theRipper = new TheRipper(attackerCard);
            theRipper.cardAbility(new Minion(attackedCard));
        } else if(attackerCard.getCard().getName().equals("The Cursed One")) {
            TheCursedOne theCursedOne = new TheCursedOne(attackerCard);
            theCursedOne.cardAbility(new Minion(attackedCard));
        } else if(attackerCard.getCard().getName().equals("Disciple")) {
            Disciple disciple = new Disciple(attackerCard);
            disciple.cardAbility(new Minion(attackedCard));
        }


        if(attackedCard.getCard().getHealth() <= 0) {
            Table.getTable().get(targetX).remove(targetY);
        }
        attackerCard.setAlreadyAttacked(1);
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

    public void makeErrorNodes(ObjectMapper objectMapper, int attackerX, int attackerY, int targetX, int targetY, String string, ArrayNode output) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        ObjectNode cardAttackerNode = objectMapper.createObjectNode();
        ObjectNode cardAttackedNode = objectMapper.createObjectNode();
        errorNode.put("command", "cardUsesAbility");
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
