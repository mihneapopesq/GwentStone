package utilities;

import fileio.CardInput;
import utilities.minions.Minion;

import java.util.ArrayList;

public class Hero extends Card{

    private int health;

    public Hero(CardInput card){
        super(card);
        this.health = 30;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void heroAbility(ArrayList<Card> row) {

    }


}
