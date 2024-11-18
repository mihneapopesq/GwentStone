package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import java.util.Random;
import utilities.Table;
import java.util.Collections;

import fileio.Input;
import fileio.CardInput;
import fileio.ActionsInput;
import fileio.StartGameInput;
import utilities.Player;
import utilities.Hand;
import utilities.Card;
import utilities.commands.GetPlayerDeck;
import utilities.commands.GetPlayerHero;
import utilities.commands.GetPlayerTurn;
import utilities.commands.GetCardsInHand;
import utilities.commands.PlaceCard;
import utilities.commands.GetPlayerMana;
import utilities.commands.GetCardsOnTable;
import utilities.commands.CardUsesAttack;
import utilities.commands.GetCardAtPosition;
import utilities.commands.CardUsesAbility;
import utilities.commands.UseAttackHero;
import utilities.commands.UseHeroAbility;
import utilities.commands.GetFrozenCardsOnTable;
import utilities.commands.GetTotalGamesPlayed;
import utilities.commands.GetPlayerTwoWins;
import utilities.commands.GetPlayerOneWins;
import utilities.ConstantsConfig;
/**
 * Main class for starting and running the game
 */
public final class Start {

    private int currentRound = 1;
    private static final int HERO_INITIAL_HEALTH = ConstantsConfig.HERO_INITIAL_HEALTH;

    private final Player[] players = new Player[2];
    private final Table table = new Table();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Input input;
    private final ArrayNode output;
    private int playerTurn;
    private int startingPlayer;
    private int manaToIncrement = 1;
    private int player1Wins;
    private int player2Wins;

    /**
     * Constructor for the Start class
     *
     * @param input  the input object containing game data
     * @param output the output node for results
     */
    public Start(final Input input, final ArrayNode output) {
        players[0] = new Player();
        players[1] = new Player();
        players[0].setHand(new Hand());
        players[1].setHand(new Hand());
        players[0].getHand().setCards(new ArrayList<>());
        players[1].getHand().setCards(new ArrayList<>());
        this.input = input;
        this.output = output;

        player1Wins = 0;
        player2Wins = 0;
    }

    /**
     * Initializes the players' decks and heroes for a game
     *
     * @param i the index of the current game
     */
    private void initializeDecks(final int i) {
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

        players[0].getHero().setHealth(HERO_INITIAL_HEALTH);
        players[1].getHero().setHealth(HERO_INITIAL_HEALTH);

        players[0].getHand().getCards().add(new Card(players[0].getDeck().remove(0)));
        players[1].getHand().getCards().add(new Card(players[1].getDeck().remove(0)));

        players[0].setMana(1);
        players[1].setMana(1);

        this.playerTurn = startGame.getStartingPlayer();
    }

    /**
     * Runs the game based on the input data
     */
    public void run() {
        for (int i = 0; i < input.getGames().size(); i++) {

            manaToIncrement = 1;

            clearTable(table);
            clearDeck(players[0].getDeck());
            clearDeck(players[1].getDeck());
            clearHand(players[0].getHand());
            clearHand(players[1].getHand());



            initializeDecks(i);
            ObjectMapper mapper = new ObjectMapper();
            startingPlayer = input.getGames().get(i).getStartGame().getStartingPlayer();
            ArrayList<ActionsInput> actionsInputs = input.getGames().get(i).getActions();


            for (ActionsInput action : actionsInputs) {
                ObjectNode actionNode = mapper.createObjectNode();

                handleAction(action, actionNode, players, i);

                if (players[0].getGameEnded() == 1) {
                    players[0].setGameEnded(0);
                    player1Wins++;
                }
                if (players[1].getGameEnded() == 1) {
                    players[1].setGameEnded(0);
                    player2Wins++;
                }
            }
        }
    }

    /**
     * Handles the command from the input and runs the action
     *
     * @param action       the action input
     * @param actionNode   the action node for output
     * @param player       the players array
     * @param currentGame  the current game index
     */
    public void handleAction(final ActionsInput action, final ObjectNode actionNode,
                             final Player[] player, final int currentGame) {
        String command = action.getCommand();
        if (command.equals("getPlayerDeck")) {
            GetPlayerDeck getPlayerDeckInstance
                    = new GetPlayerDeck();
            if (action.getPlayerIdx() == 1) {
                getPlayerDeckInstance.getPlayerDeck(action, actionNode, player[0],
                        objectMapper, output);
            } else {
                getPlayerDeckInstance.getPlayerDeck(action, actionNode, player[1],
                        objectMapper, output);
            }
        } else if (command.equals("getPlayerHero")) {
            GetPlayerHero getPlayerHeroInstance
                    = new GetPlayerHero();
            if (action.getPlayerIdx() == 1) {
                getPlayerHeroInstance.getPlayerHero(action, actionNode, player[0], output);
            } else {
                getPlayerHeroInstance.getPlayerHero(action, actionNode, player[1], output);
            }
        } else if (command.equals("getPlayerTurn")) {
            GetPlayerTurn getPlayerTurnInstance
                    = new GetPlayerTurn(playerTurn);
            getPlayerTurnInstance.getPlayerTurn(actionNode, output);
        } else if (command.equals("endPlayerTurn")) {

            if (playerTurn == 1) {
                for (int j = 2; j < ConstantsConfig.PLAYER_ONE_END_ROW; j++) {
                    for (Card card : table.getTable().get(j)) {
                        card.setIsFrozen(0);
                    }
                }
            } else {
                for (int j = 0; j < ConstantsConfig.PLAYER_TWO_END_ROW; j++) {
                    for (Card card : table.getTable().get(j)) {
                        card.setIsFrozen(0);
                    }
                }
            }

            if (playerTurn == 1) {
                playerTurn = 2;
            } else {
                playerTurn = 1;
            }
            if (playerTurn == startingPlayer) {
                currentRound++;
                manaToIncrement++;
                if (manaToIncrement > ConstantsConfig.MAX_MANA) {
                    manaToIncrement = ConstantsConfig.MAX_MANA;
                }
                player[0].setMana(player[0].getMana() + manaToIncrement);
                player[1].setMana(player[1].getMana() + manaToIncrement);
                // take first card from deck
                if (!player[0].getDeck().isEmpty()) {
                    player[0].getHand().getCards().add(new Card(player[0].getDeck().get(0)));
                    player[0].getDeck().remove(0);
                }
                if (!player[1].getDeck().isEmpty()) {
                    player[1].getHand().getCards().add(new Card(player[1].getDeck().get(0)));
                    player[1].getDeck().remove(0);
                }
                for (int i = 0; i < ConstantsConfig.MAX_ROWS; i++) {
                    for (Card card : table.getTable().get(i)) {
                        card.setAlreadyAttacked(0);
                    }
                }
                player[0].setHeroAlreadyAttacked(0);
                player[1].setHeroAlreadyAttacked(0);
            }
        } else if (command.equals("getCardsInHand")) {
            GetCardsInHand getCardsInHandInstance
                    = new GetCardsInHand();
            if (action.getPlayerIdx() == 1) {
                getCardsInHandInstance.getCardsInHand(action, actionNode,
                        player[0], objectMapper, output);
            } else {
                getCardsInHandInstance.getCardsInHand(action, actionNode,
                        player[1], objectMapper, output);
            }
        } else if (command.equals("placeCard")) {
            int handIdx = action.getHandIdx();
            PlaceCard placeCardInstance
                    = new PlaceCard();
            placeCardInstance.placeCard(player, playerTurn, handIdx, table,
                    objectMapper, output);
        } else if (command.equals("getPlayerMana")) {
            GetPlayerMana getPlayerManaInstance
                    = new GetPlayerMana();
            if (action.getPlayerIdx() == 1) {
                getPlayerManaInstance.getPlayerMana(action, actionNode,
                        player[0], output);
            } else {
                getPlayerManaInstance.getPlayerMana(action, actionNode,
                        player[1], output);
            }
        } else if (command.equals("getCardsOnTable")) {
            GetCardsOnTable getCardsOnTableInstance
                    = new GetCardsOnTable();
            getCardsOnTableInstance.getCardsOnTable(actionNode, table,
                    objectMapper, output);
        } else if (command.equals("cardUsesAttack")) {
            CardUsesAttack cardUsesAttackInstance
                    = new CardUsesAttack();
            cardUsesAttackInstance.cardUsesAttack(action, playerTurn - 1,
                    table, objectMapper, output);
        } else if (command.equals("getCardAtPosition")) {
            GetCardAtPosition getCardAtPositionInstance
                    = new GetCardAtPosition();
            getCardAtPositionInstance.getCardAtPosition(action, table, objectMapper, output);
        } else if (command.equals("cardUsesAbility")) {
            CardUsesAbility cardUsesAbilityInstance =
                    new CardUsesAbility();
            cardUsesAbilityInstance.cardUsesAbility(action, playerTurn - 1,
                    table, objectMapper, output);
        } else if (command.equals("useAttackHero")) {
            UseAttackHero useAttackHeroInstance
                    = new UseAttackHero();
            useAttackHeroInstance.useAttackHero(action, playerTurn - 1,
                    table, objectMapper, output, player);
        } else if (command.equals("useHeroAbility")) {
            UseHeroAbility useHeroAbilityInstance
                    = new UseHeroAbility();
            useHeroAbilityInstance.useHeroAbility(action, playerTurn - 1,
                    table, objectMapper, output, player);
        } else if (command.equals("getFrozenCardsOnTable")) {
            GetFrozenCardsOnTable getFrozenCardsOnTableInstance
                    = new GetFrozenCardsOnTable();
            getFrozenCardsOnTableInstance.getFrozenCardsOnTable(table, objectMapper,
                    output, actionNode);
        } else if (command.equals("getTotalGamesPlayed")) {
            GetTotalGamesPlayed getTotalGamesPlayedInstance
                    = new GetTotalGamesPlayed();
            getTotalGamesPlayedInstance.getTotalGamesPlayed(objectMapper, output,
                    currentGame + 1);
        } else if (command.equals("getPlayerTwoWins")) {
            GetPlayerTwoWins getPlayerTwoWinsInstance
                    = new GetPlayerTwoWins();
            getPlayerTwoWinsInstance.getPlayerTwoWins(objectMapper, output, player2Wins);
        } else if (command.equals("getPlayerOneWins")) {
            GetPlayerOneWins getPlayerOneWinsInstance
                    = new GetPlayerOneWins();
            getPlayerOneWinsInstance.getPlayerOneWins(objectMapper, output, player1Wins);
        }
    }

    /**
     * Clears the table for a new game
     *
     * @param localTable the table to clear
     */
    public void clearTable(final Table localTable) {
        for (int i = 0; i < ConstantsConfig.MAX_ROWS; i++) {
            localTable.getTable().get(i).clear();
        }
    }


    /**
     * Clears a player's deck for a new game
     *
     * @param deck the deck to clear
     */
    public void clearDeck(final ArrayList<CardInput> deck) {
        for (int i = 0; i < deck.size(); i++) {
            deck.remove(i);
        }
    }

    /**
     * Clears a player's hand for a new game
     *
     * @param hand the hand to clear
     */
    public void clearHand(final Hand hand) {
        for (int i = 0; i < hand.getCards().size(); i++) {
            hand.getCards().remove(i);
        }
    }

}
