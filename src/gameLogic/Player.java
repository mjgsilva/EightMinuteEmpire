package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable  {
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
        // Add 14 Army man to player
        for (int i = 0; i < 14; i++)
            armies.add(new Army(id, color));
    }

    public int getScore() {
        return score;
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

    public String getColor() {
        return color;
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

    public Card getLastCard() {
        return cards.get(cards.size()-1);
    }

    public Army getArmy() {
        Army aux = armies.get(armies.size()-1);
        armies.remove(aux);
        return aux;
    }
    
    public void addArmy(Army army) {
        armies.add(army);
    }
            
    
    public ArrayList<Army> getArmies() {
        return armies;
    }

    public void addScore(int i) {
        this.score = this.score + i;
    }

    /** Returns number of resources of received ID
     *
     * @param i
     * @return
     */
    public int getResourceUnities(int i) {
        int sum = 0;
        for (Card aux : cards) {
            if (aux.get2TypeOfResource() == i)
                sum += 1 * aux.getNumberOfResource();
        }
        return sum;
    }
}