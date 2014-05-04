package gameLogic.states;

import gameLogic.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class StateAdapter implements StateInterface, Serializable {
    private Game game;
    
    public StateAdapter (Game game) {
        this.game = game;
    }
    
    protected Game getGame() {
        return this.game;
    }
    
    @Override
    public StateInterface defineGame(int n) {
        return this;
    }
    @Override
    public StateInterface defineWinner(ArrayList<Integer> bets) {
        return this;
    }
    @Override
    public StateInterface defineCard(int n) {
        return this;
    }
    @Override
    public StateInterface defineAction(int n) {
        return this;
    }    
}
