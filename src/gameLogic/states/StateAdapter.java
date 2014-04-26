package gameLogic.states;

import gameLogic.*;
import java.util.ArrayList;

public abstract class StateAdapter implements StateInterface {
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
    public StateInterface defineCard() {
        return this;
    }
    @Override
    public StateInterface defineArmy() {
        return this;
    }
    @Override
    public StateInterface defineMoveByLand() {
        return this;
    }
    @Override
    public StateInterface defineMoveBySea() {
        return this;
    }
    @Override
    public StateInterface defineCity() {
        return this;
    }
    @Override
    public StateInterface defineNeutralize() {
        return this;
    }
    
    @Override
    public StateInterface placeArmyOrCity() {
        return this;
    }
    @Override
    public StateInterface moveArmy() {
        return this;
    }
    @Override
    public StateInterface neutralize() {
        return this;
    }
    @Override
    public StateInterface reDo() {
        return this;
    }
    @Override
    public StateInterface newArmyThenNeutralize() {
        return this;
    }    
    
    @Override
    public StateInterface saveToFile() {
        return this;
    }
    @Override
    public StateInterface loadFromFile() {
        return this;
    }
}
