package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import fileio.*;
import utilities.Player;

public final class Start {

    private int current_round = 0;
    private Player[] players = new Player[2];
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<GameInput> games;
    private Input input;
    private ArrayNode output;
    private ArrayList<CardInput> deck1;
    private ArrayList<CardInput> deck2;
    private ArrayList<ActionsInput> actionsinputs;

    public Start(Input input, ArrayNode output) {
        players[0] = new Player();
        players[1] = new Player();
        this.input = input;
        this.output = output;
        initializeDecks();
    }

    private void initializeDecks() {
        DecksInput decksInput1 = input.getPlayerOneDecks();
        DecksInput decksInput2 = input.getPlayerTwoDecks();

        deck1 = decksInput1.getDecks().get(0);
        deck2 = decksInput2.getDecks().get(1);

        players[0].setDeck(deck1);
        players[1].setDeck(deck2);
    }

    public ObjectNode cardNode(CardInput card) {
        ObjectNode cardNode = objectMapper.createObjectNode();

        if (card.getAttackDamage() > 0) {
            cardNode.put("health", card.getHealth())
                    .put("attackDamage", card.getAttackDamage());
        } else {
            cardNode.put("health", card.getHealth());
        }

        cardNode.put("mana", card.getMana())
                .put("description", card.getDescription())
                .put("name", card.getName())
                .set("colors", getCardColors(card));

        return cardNode;
    }

    private ArrayNode getCardColors(CardInput card) {
        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : card.getColors()) {
            colorsNode.add(color);
        }
        return colorsNode;
    }

    public ArrayNode cardArrayNode(ArrayList<CardInput> cards) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (CardInput card : cards) {
            arrayNode.add(cardNode(card));
        }
        return arrayNode;
    }

    public void run() {
        // Itereaza prin jocuri
        for (int i = 0; i < input.getGames().size(); i++) {
            ObjectMapper mapper = new ObjectMapper();
            StartGameInput startGame = input.getGames().get(i).getStartGame();
            int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
            int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();

            System.out.printf("player 1 idx %d; ", playerOneDeckIdx);
            System.out.printf("player 2 idx %d\n", playerTwoDeckIdx);

            players[0].setDeck(input.getPlayerOneDecks().getDecks().get(playerOneDeckIdx));
            players[1].setDeck(input.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx));

            actionsinputs = input.getGames().get(0).getActions();

            for (ActionsInput action : actionsinputs) {
                ObjectNode actionNode = mapper.createObjectNode();
                handleAction(action, actionNode, players);
            }
        }
    }

    public void handleAction(ActionsInput action, ObjectNode actionNode, Player[] player) {
        String command = action.getCommand();
        if (command.equals("getPlayerDeck")) {
            // TODO: Implement getPlayerDeck
            if(action.getPlayerIdx() == 0) {
                getPlayerDeck(action, actionNode, player[0]);
            } else {
                getPlayerDeck(action, actionNode, player[1]);
            }
        } else if (command.equals("getPlayerHero")) {
            // TODO: Implement getPlayerHero
        } else if (command.equals("getPlayerTurn")) {
            // TODO: Implement getPlayerTurn
        } else if (command.equals("getCardsInHand")) {
            // TODO: Implement getCardsInHand
        } else if (command.equals("getPlayerMana")) {
            // TODO: Implement getPlayerMana
        } else if (command.equals("getCardsOnTable")) {
            // TODO: Implement getCardsOnTable
        }
    }
    public void getPlayerDeck(ActionsInput action, ObjectNode actionNode,Player player) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerDeck");
        actionNode.put("playerIdx", playerIdx);
        actionNode.set("output", cardArrayNode(player.getDeck()));
        output.add(actionNode);
    }
}
