package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import java.util.Random;

import java.util.Collections;

import fileio.*;
import utilities.Card;
import utilities.Hero;
import utilities.Player;

public final class Start {

    private int current_round = 0;
    private Player[] players = new Player[2];
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<GameInput> games;
    private Input input;
    private ArrayNode output;
    private ArrayList<CardInput> deckPlayer1;
    private ArrayList<CardInput> deckPlayer2;
    private ArrayList<ActionsInput> actionsinputs;
    private int playerTurn;

    //pare ok
    public Start(Input input, ArrayNode output) {
        players[0] = new Player();
        players[1] = new Player();
        this.input = input;
        this.output = output;
        deckPlayer1 = new ArrayList<CardInput>();
        deckPlayer2 = new ArrayList<CardInput>();
    }
    //pare ok
    private void initializeDecks(int i) {
        StartGameInput startGame = input.getGames().get(i).getStartGame();
        int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();

        // make the first deck
        for(int j = 0; j < input.getPlayerOneDecks().getNrCardsInDeck(); j++) {
            CardInput card = input.getPlayerOneDecks().getDecks().get(playerOneDeckIdx).get(j);
            deckPlayer1.add(card);
        }

        // make the second deck
        for(int j = 0; j < input.getPlayerTwoDecks().getNrCardsInDeck(); j++) {
            CardInput card = input.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx).get(j);
            deckPlayer2.add(card);
        }

        // set the decks
        int shuffleSeed = startGame.getShuffleSeed();
        players[0].setDeck(deckPlayer1);
        players[1].setDeck(deckPlayer2);
        // shuffle the decks
        Collections.shuffle(players[0].getDeck(), new Random(shuffleSeed));
        Collections.shuffle(players[1].getDeck(), new Random(shuffleSeed));

        // set the heroes
        players[0].setHero(startGame.getPlayerOneHero());
        players[1].setHero(startGame.getPlayerTwoHero());

        // set heroes health
        players[0].getHero().setHealth(30);
        players[1].getHero().setHealth(30);

        this.playerTurn = startGame.getStartingPlayer();
    }
    public void run() {
        for (int i = 0; i < input.getGames().size(); i++) {
            initializeDecks(i);
            ObjectMapper mapper = new ObjectMapper();
            int startingPlayer = input.getGames().get(i).getStartGame().getStartingPlayer();
            actionsinputs = input.getGames().get(i).getActions();
            for (ActionsInput action : actionsinputs) {
                ObjectNode actionNode = mapper.createObjectNode();
                handleAction(action, actionNode, players);
            }
        }
    }
    public void handleAction(ActionsInput action, ObjectNode actionNode, Player[] player) {
        String command = action.getCommand();
        if (command.equals("getPlayerDeck")) {
            utilities.commands.getPlayerDeck getPlayerDeckInstance = new utilities.commands.getPlayerDeck();
            if(action.getPlayerIdx() == 1) {
                getPlayerDeckInstance.getPlayerDeck(action, actionNode, player[0], objectMapper, output);
            } else {
                getPlayerDeckInstance.getPlayerDeck(action, actionNode, player[1], objectMapper, output);
            }
        } else if (command.equals("getPlayerHero")) {
            utilities.commands.getPlayerHero getPlayerHeroInstance = new utilities.commands.getPlayerHero();
            if(action.getPlayerIdx() == 1) {
                getPlayerHeroInstance.getPlayerHero(action, actionNode, player[0], output);
            } else {
                getPlayerHeroInstance.getPlayerHero(action, actionNode, player[1], output);
            }
        } else if (command.equals("getPlayerTurn")) {
            utilities.commands.getPlayerTurn getPlayerTurnInstance = new utilities.commands.getPlayerTurn(playerTurn);
            getPlayerTurnInstance.getPlayerTurn(action, actionNode, output);
        } else if (command.equals("getCardsInHand")) {
            // TODO: Implement getCardsInHand
        } else if (command.equals("getPlayerMana")) {
            // TODO: Implement getPlayerMana
        } else if (command.equals("getCardsOnTable")) {
            // TODO: Implement getCardsOnTable
        }
    }
}
