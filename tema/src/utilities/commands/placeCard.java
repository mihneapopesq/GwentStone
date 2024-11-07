package utilities.commands;

import utilities.Player;
import utilities.Card;
import utilities.Table;

import java.util.ArrayList;

public class placeCard {

    //logica la mana e bubuita + fa si getCards on Table
    public void placeCard(Player[] players, int playerTurn, int handIdx, Table table) {
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
        System.out.printf("Target row: %d\n", targetRow);
        if (table.getTable().get(targetRow).size() < 5 && currentPlayer.getMana() >= cardToPlace.getCard().getMana()) {
            table.getTable().get(targetRow).add(cardToPlace);
            System.out.printf("mana: %d - %d = %d\n", players[playerTurn - 1].getMana(), cardToPlace.getCard().getMana(), players[playerTurn - 1].getMana() - cardToPlace.getCard().getMana());
            currentPlayer.setMana(currentPlayer.getMana() - cardToPlace.getCard().getMana());
            currentPlayer.getHand().getCards().remove(handIdx);
        } else {
            System.out.println("No space in the target row to place the card.");
        }
    }
}

