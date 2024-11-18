package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utilities.ConstantsConfig;
import utilities.Player;
import utilities.Card;
import utilities.Table;

/**
 * Handles the logic for placing a card on the table
 */
public final class PlaceCard {

    /**
     * Places a card from the player's hand onto the table
     *
     * @param players      the array of players
     * @param playerTurn   the current player's turn
     * @param handIdx      the index of the card in the player's hand
     * @param table        the game table
     * @param objectMapper the object mapper for creating JSON nodes
     * @param output       the output array for storing results
     */
    public void placeCard(final Player[] players, final int playerTurn,
                          final int handIdx, final Table table,
                          final ObjectMapper objectMapper,
                          final ArrayNode output) {
        Player currentPlayer = players[playerTurn - 1];

        // Check if the hand index is valid
        if (handIdx >= currentPlayer.getHand().getCards().size()) {
            return;
        }

        Card cardToPlace = currentPlayer.getHand().getCards().get(handIdx);
        int targetRow = determineTargetRow(cardToPlace, playerTurn);

        // Check if the player has enough mana to place the card
        if (currentPlayer.getMana() < cardToPlace.getCard().getMana()) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "placeCard");
            errorNode.put("error", "Not enough mana to place card on table.");
            errorNode.put("handIdx", handIdx);
            output.add(errorNode);
            return;
        }

        // Check if the target row has space
        if (table.getTable().get(targetRow).size() < ConstantsConfig.MAX_CARDS_PER_ROW) {
            table.getTable().get(targetRow).add(cardToPlace);
            currentPlayer.setMana(currentPlayer.getMana() - cardToPlace.getCard().getMana());
            currentPlayer.getHand().getCards().remove(handIdx);
        } else {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "placeCard");
            errorNode.put("error", "Target row is full.");
            errorNode.put("handIdx", handIdx);
            output.add(errorNode);
        }
    }

    /**
     * Determines the target row on the table based on the card type and player turn
     *
     * @param card        the card to place
     * @param playerTurn  the current player's turn
     * @return the target row index
     */
    private int determineTargetRow(final Card card, final int playerTurn) {
        String cardName = card.getCard().getName();
        if ("Sentinel".equals(cardName) || "Berserker".equals(cardName)
                || "The Cursed One".equals(cardName) || "Disciple".equals(cardName)) {
            if (playerTurn == 1) {
                return ConstantsConfig.PLAYER_ONE_BACK_ROW;
            } else {
                return 0;
            }

        } else if ("Goliath".equals(cardName) || "Warden".equals(cardName)
                || "Miraj".equals(cardName) || "The Ripper".equals(cardName)) {
            if (playerTurn == 1) {
                return 2;
            } else {
                return 1;
            }
        }
        return -1; // Error
    }
}
