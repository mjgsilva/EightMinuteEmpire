package gameLogic;

import java.util.ArrayList;

public class Player {
    // Player id
    private final int id;
    // Not in game armies
    private final ArrayList <Army> armies = new ArrayList<>(); 
    private int coins;
    private final ArrayList <Card> cards = new ArrayList<>();
    private int score = 0;
    
    public Player(int id) {
        this.id = id;
        // Add 14 Army man to player
        for (int i = 0; i < 14; i++)
            armies.add(new Army(id));
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public void useCoins(int cost) {
        this.coins = this.coins - cost;
    }
}
