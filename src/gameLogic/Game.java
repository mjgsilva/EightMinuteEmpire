package gameLogic;

import gameLogic.states.*;
import java.util.ArrayList;

public class Game {
    private StateInterface state;
    private ArrayList<Player> players = new ArrayList<>();
    
    private Player currentPlayer;

    public Game() {
        state = new PrepareGame(this);
    }

    public StateInterface getState() {
        return state;
    }
    
    
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    
    
    
    
    
    
    
    
    
    
    
    public void defineGame(int n) {
        state = state.defineGame(n);
    }

    public void defineWinner(ArrayList<Integer> bets) {
        state = state.defineWinner(bets);
    }
}