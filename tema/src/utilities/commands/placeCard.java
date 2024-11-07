package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.Player;
import utilities.Card;
import utilities.Table;

import java.util.ArrayList;

public class placeCard {

    //logica la mana e bubuita + fa si getCards on Table
    public void placeCard(Player[] players, int playerTurn, int handIdx, Table table, ObjectNode actionNode, ObjectMapper objectMapper, ArrayNode output) {
        Player currentPlayer = players[playerTurn - 1];
        if(handIdx >= currentPlayer.getHand().getCards().size())
            return ;
        Card cardToPlace = currentPlayer.getHand().getCards().get(handIdx);
        int targetRow = 0;
        if (cardToPlace.getCard().getName().equals("Sentinel") || cardToPlace.getCard().getName().equals("Berserker")
                || cardToPlace.getCard().getName().equals("The Cursed One")
                || cardToPlace.getCard().getName().equals("Disciple")){
            targetRow = playerTurn == 1 ? 3 : 0;
        } else if (cardToPlace.getCard().getName().equals("Goliath") || cardToPlace.getCard().getName().equals("Warden")
                || cardToPlace.getCard().getName().equals("Miraj")
                || cardToPlace.getCard().getName().equals("The Ripper")) {
            targetRow = playerTurn == 1 ? 2 : 1;
        }
        if(currentPlayer.getMana() < cardToPlace.getCard().getMana()){
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "placeCard");
            errorNode.put("error", "Not enough mana to place card on table.");
            errorNode.put("handIdx", handIdx);
            output.add(errorNode);
        }

        if (table.getTable().get(targetRow).size() < 5) {
            if(currentPlayer.getMana() >= cardToPlace.getCard().getMana()) {
                table.getTable().get(targetRow).add(cardToPlace);
                currentPlayer.setMana(currentPlayer.getMana() - cardToPlace.getCard().getMana());
                currentPlayer.getHand().getCards().remove(handIdx);
            }
        }
    }
}

