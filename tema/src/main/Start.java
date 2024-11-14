package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import java.util.Random;
import utilities.Table;
import java.util.Collections;

import fileio.*;
import utilities.*;

public final class Start {

    private int current_round = 1;
    private Player[] players = new Player[2];
    private Table Table = new Table();
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<GameInput> games;
    private Input input;
    private ArrayNode output;
    private ArrayList<CardInput> deckPlayer1;
    private ArrayList<CardInput> deckPlayer2;
    private ArrayList<ActionsInput> actionsinputs;
    private int playerTurn;
    private int startingPlayer;
    private int manaToIncrement = 1;
    private int player1Wins;
    private int player2Wins;

    //pare ok
    public Start(Input input, ArrayNode output) {
        players[0] = new Player();
        players[1] = new Player();
        players[0].setHand(new Hand());
        players[1].setHand(new Hand());
        players[0].getHand().setCards(new ArrayList<Card>());
        players[1].getHand().setCards(new ArrayList<Card>());
        this.input = input;
        this.output = output;
        players[0].setWins(0);
        players[1].setWins(0);
        player1Wins = 0;
        player2Wins = 0;
    }

    private void initializeDecks(int i) {
        StartGameInput startGame = input.getGames().get(i).getStartGame();
        int playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        int playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();

        ArrayList<CardInput> deck1 = input.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
        ArrayList<CardInput> deck2 = input.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

        players[0] = new Player(deck1);
        players[1] = new Player(deck2);

        int shuffleSeed = startGame.getShuffleSeed();
        Collections.shuffle(players[0].getDeck(), new Random(shuffleSeed));
        Collections.shuffle(players[1].getDeck(), new Random(shuffleSeed));

        players[0].setHero(startGame.getPlayerOneHero());
        players[1].setHero(startGame.getPlayerTwoHero());

        players[0].getHero().setHealth(30);
        players[1].getHero().setHealth(30);

        players[0].getHand().getCards().add(new Card(players[0].getDeck().remove(0)));
        players[1].getHand().getCards().add(new Card(players[1].getDeck().remove(0)));

        players[0].setMana(1);
        players[1].setMana(1);

        this.playerTurn = startGame.getStartingPlayer();
    }


    public void run() {
        for (int i = 0; i < input.getGames().size(); i++) {


            current_round = 1;
            manaToIncrement = 1;

            clearTable(Table);
            clearDeck(players[0].getDeck());
            clearDeck(players[1].getDeck());
            clearHand(players[0].getHand());
            clearHand(players[1].getHand());



            initializeDecks(i);
            ObjectMapper mapper = new ObjectMapper();
            startingPlayer = input.getGames().get(i).getStartGame().getStartingPlayer();
            actionsinputs = input.getGames().get(i).getActions();




            for (ActionsInput action : actionsinputs) {
                ObjectNode actionNode = mapper.createObjectNode();

                handleAction(action, actionNode, players, i);

                if(players[0].getGameEnded() == 1) {
                    players[0].setGameEnded(0);
                    player1Wins++;
                }
                if(players[1].getGameEnded() == 1) {
                    players[1].setGameEnded(0);
                    player2Wins++;
                }
            }
        }
    }
    public void handleAction(ActionsInput action, ObjectNode actionNode, Player[] player, int current_game) {
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
        } else if (command.equals("endPlayerTurn")) {

            if (playerTurn == 1) {
                for (int j = 2; j < 4; j++) {
                    for (Card card : Table.getTable().get(j))
                        card.setIsFrozen(0);
                }
            } else {
                for (int j = 0; j < 2; j++) {
                    for(Card card : Table.getTable().get(j))
                        card.setIsFrozen(0);
                }
            }

            playerTurn = (playerTurn == 1) ? 2 : 1;
            if(playerTurn == startingPlayer) {
                current_round++;
                manaToIncrement++;
                if(manaToIncrement > 10)
                    manaToIncrement = 10;
                player[0].setMana(player[0].getMana() + manaToIncrement);
                player[1].setMana(player[1].getMana() + manaToIncrement);
                // take first card from deck
                if (player[0].getDeck().size() > 0) {
                    player[0].getHand().getCards().add(new Card(player[0].getDeck().get(0)));
                    player[0].getDeck().remove(0);
                }
                if (player[1].getDeck().size() > 0) {
                    player[1].getHand().getCards().add(new Card(player[1].getDeck().get(0)));
                    player[1].getDeck().remove(0);
                }
                for(int i = 0; i < 4; i++) {
                    for(Card card : Table.getTable().get(i)) {
                        card.setAlreadyAttacked(0);
                    }
                }
                player[0].setHeroAlreadyAttacked(0);
                player[1].setHeroAlreadyAttacked(0);
            }
        } else if(command.equals("getCardsInHand")) {
            utilities.commands.getCardsInHand getCardsInHandInstance = new utilities.commands.getCardsInHand();
            if(action.getPlayerIdx() == 1) {
                getCardsInHandInstance.getCardsInHand(action, actionNode, player[0], objectMapper, output);
            } else {
                getCardsInHandInstance.getCardsInHand(action, actionNode, player[1], objectMapper, output);
            }
        } else if(command.equals("placeCard")) {
            int handIdx = action.getHandIdx();
            utilities.commands.placeCard placeCardInstance = new utilities.commands.placeCard();
            placeCardInstance.placeCard(player, playerTurn, handIdx, Table, actionNode, objectMapper, output);
        } else if(command.equals("getPlayerMana")) {
            utilities.commands.getPlayerMana getPlayerManaInstance = new utilities.commands.getPlayerMana();
            if(action.getPlayerIdx() == 1) {
                getPlayerManaInstance.getPlayerMana(action, actionNode, player[0], output);
            } else {
                getPlayerManaInstance.getPlayerMana(action, actionNode, player[1], output);
            }
        } else if(command.equals("getCardsOnTable")) {
            utilities.commands.getCardsOnTable getCardsOnTableInstance = new utilities.commands.getCardsOnTable();
            getCardsOnTableInstance.getCardsOnTable(action, actionNode, Table, objectMapper, output);
        } else if(command.equals("cardUsesAttack")) {
            utilities.commands.cardUsesAttack cardUsesAttackInstance = new utilities.commands.cardUsesAttack();
            cardUsesAttackInstance.cardUsesAttack(action,playerTurn - 1, Table, objectMapper, output);
        } else if(command.equals("getCardAtPosition")) {
            utilities.commands.getCardAtPosition getCardAtPositionInstance = new utilities.commands.getCardAtPosition();
            getCardAtPositionInstance.getCardAtPosition(action, actionNode, Table, objectMapper, output);
        } else if(command.equals("cardUsesAbility")) {
            utilities.commands.cardUsesAbility cardUsesAbilityInstance = new utilities.commands.cardUsesAbility();
            cardUsesAbilityInstance.cardUsesAbility(action, playerTurn - 1, Table, objectMapper, output);
        } else if(command.equals("useAttackHero")) {
            utilities.commands.useAttackHero useAttackHeroInstance = new utilities.commands.useAttackHero();
            useAttackHeroInstance.useAttackHero(action, playerTurn - 1, Table, objectMapper, output, player);
        } else if(command.equals("useHeroAbility")) {
            utilities.commands.useHeroAbility useHeroAbilityInstance = new utilities.commands.useHeroAbility();
            useHeroAbilityInstance.useHeroAbility(action, playerTurn - 1, Table, objectMapper, output, player);
        } else if(command.equals("getFrozenCardsOnTable")) {
            utilities.commands.getFrozenCardsOnTable getFrozenCardsOnTableInstance = new utilities.commands.getFrozenCardsOnTable();
            getFrozenCardsOnTableInstance.getFrozenCardsOnTable(Table, objectMapper, output, actionNode);
        } else if(command.equals("getTotalGamesPlayed")) {
            utilities.commands.getTotalGamesPlayed getTotalGamesPlayedInstance = new utilities.commands.getTotalGamesPlayed();
            getTotalGamesPlayedInstance.getTotalGamesPlayed(objectMapper, output, current_game + 1);
        } else if(command.equals("getPlayerTwoWins")) {
            utilities.commands.getPlayerTwoWins getPlayerTwoWinsInstance = new utilities.commands.getPlayerTwoWins();
            getPlayerTwoWinsInstance.getPlayerTwoWins(objectMapper, output, player2Wins);
        } else if(command.equals("getPlayerOneWins")) {
            utilities.commands.getPlayerOneWins getPlayerOneWinsInstance = new utilities.commands.getPlayerOneWins();
            getPlayerOneWinsInstance.getPlayerOneWins(objectMapper, output, player1Wins);
        }
    }

    public void clearTable(Table table){
        for(int i = 0; i < 4; i++){
            table.getTable().get(i).clear();
        }
    }

    public void clearDeck(ArrayList<CardInput> deck){
        for(int i = 0; i < deck.size(); i++){
            deck.remove(i);
        }
    }

    public void clearHand(Hand hand){
        for(int i = 0; i < hand.getCards().size(); i++){
            hand.getCards().remove(i);
        }
    }

}
