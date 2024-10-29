package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import fileio.Input;
import utilities.requirements.Player;
import fileio.GameInput;
import fileio.DecksInput;

public final class Start {

    private int current_round = 0;
    private Player [] players = new Player[2];
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<GameInput> games;

    public Start(final Input input, ArrayNode output) {
        this.players[0] = new Player();
        this.players[1] = new Player();
    }

    public final void run(ArrayNode output){

    }


}
