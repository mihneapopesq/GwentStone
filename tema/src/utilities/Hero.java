package utilities;

import fileio.CardInput;

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
}
