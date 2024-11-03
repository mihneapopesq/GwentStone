package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import java.util.Random;

import java.util.Collections;

import fileio.*;
import utilities.Hero;
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
    private int playerTurn;

    //pare ok
    public Start(Input input, ArrayNode output) {
        players[0] = new Player();
        players[1] = new Player();
        this.input = input;
        this.output = output;
    }


    //pare ok
    private void initializeDecks(int i) {
        DecksInput decksInput1 = input.getPlayerOneDecks();
        DecksInput decksInput2 = input.getPlayerTwoDecks();

        StartGameInput startGame = input.getGames().get(i).getStartGame();
        int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();

        deck1 = decksInput1.getDecks().get(playerOneDeckIdx);
        deck2 = decksInput2.getDecks().get(playerTwoDeckIdx);


        int shuffleSeed = startGame.getShuffleSeed();

        Collections.shuffle(deck1, new Random(shuffleSeed));
        Collections.shuffle(deck2, new Random(shuffleSeed));

        players[0].setDeck(deck1);
        players[1].setDeck(deck2);

        players[0].setHero(startGame.getPlayerOneHero());
        players[1].setHero(startGame.getPlayerTwoHero());

        players[0].getHero().setHealth(30);
        players[1].getHero().setHealth(30);

        this.playerTurn = startGame.getStartingPlayer();

    }

    public ObjectNode cardNode(CardInput card, String s) {
        ObjectNode cardNode = objectMapper.createObjectNode();
        cardNode.put("health", card.getHealth());

        if(!s.equals("hero"))
            cardNode.put("attackDamage", card.getAttackDamage());
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

    public void makeCardArrayNode(ArrayList<CardInput> cards, ObjectNode actionNode) {
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (CardInput card : cards) {
            if (!card.getName().equals("Berserker") && !card.getName().equals("Sentinel")) {
                arrayNode.add(cardNode(card, "card"));
            }
        }
        actionNode.set("output", arrayNode);
        output.add(actionNode);
    }


    public void run() {
        // Itereaza prin jocuri
        for (int i = 0; i < input.getGames().size(); i++) {

            initializeDecks(i);
            ObjectMapper mapper = new ObjectMapper();
            int startingPlayer = input.getGames().get(i).getStartGame().getStartingPlayer();
            actionsinputs = input.getGames().get(i).getActions();
            for (ActionsInput action : actionsinputs) {

//                System.out.printf("action: %s\n", action);

                ObjectNode actionNode = mapper.createObjectNode();
                handleAction(action, actionNode, players);
            }
        }
    }

    public void handleAction(ActionsInput action, ObjectNode actionNode, Player[] player) {
        String command = action.getCommand();
        if (command.equals("getPlayerDeck")) {
            if(action.getPlayerIdx() == 1) {
                getPlayerDeck(action, actionNode, player[0]);
            } else {
                getPlayerDeck(action, actionNode, player[1]);
            }
        } else if (command.equals("getPlayerHero")) {
            if(action.getPlayerIdx() == 1) {
                getPlayerHero(action, actionNode, player[0]);
            } else {
                getPlayerHero(action, actionNode, player[1]);
            }

        } else if (command.equals("getPlayerTurn")) {
                getPlayerTurn(action, actionNode);

        } else if (command.equals("getCardsInHand")) {
            // TODO: Implement getCardsInHand
        } else if (command.equals("getPlayerMana")) {
            // TODO: Implement getPlayerMana
        } else if (command.equals("getCardsOnTable")) {
            // TODO: Implement getCardsOnTable
        }
    }

    public void getPlayerTurn(ActionsInput action, ObjectNode actionNode) {
        actionNode.put("command", "getPlayerTurn");
        actionNode.put("output", playerTurn);
        output.add(actionNode);
    }

    public void getPlayerDeck(ActionsInput action, ObjectNode actionNode, Player player) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerDeck");
        actionNode.put("playerIdx", playerIdx);
        makeCardArrayNode(player.getDeck(), actionNode);
    }

    public void makeHeroArrayNode(CardInput hero, ObjectNode actionNode) {
        ObjectNode heroNode = cardNode(hero, "hero");
        actionNode.set("output", heroNode);
        output.add(actionNode);
    }
    
    public void getPlayerHero(ActionsInput action, ObjectNode actionNode, Player player) {
        int playerIdx = action.getPlayerIdx();
        actionNode.put("command", "getPlayerHero");
        actionNode.put("playerIdx", playerIdx);
        makeHeroArrayNode(player.getHero(), actionNode);
    }

}
