package gameLogic;

import java.util.ArrayList;

public class Player {
    // Player id
    private final int id;
    // Player color - Only works on IDE console (NetBeans tested) or Unix console (jdk)
    private final String color;
    // Player counter - Not thread safe
    private static int objCounter = 0;
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
        
        objCounter++;
        // Determine Player color
        switch (objCounter) {
            case 1:
                color = "\u001B[31m"; // RED
                break;
            case 2:
                color = "\u001B[34m"; // BLUE
                break;
            case 3:
                color = "\u001B[32m"; // GREEN
                break;
            case 4:
                color = "\u001B[33m"; // YELLOW
                break;
            case 5:
                color = "\u001B[35m"; // PURPLE
                break;
            default:
                color = "";
                break;
        }
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
    
    public String getIdAsString() {
        return color + id + "\u001B[30m"; // last string is to stop coloring;
    }

    public void useCoins(int cost) {
        this.coins = this.coins - cost;
    }
    
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCardBought(Card card) {
        cards.add(card);
    }
}