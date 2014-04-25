package gameLogic.states;

public abstract class StateAdapter implements StateInterface {
    
    
    @Override
    public StateInterface defineGame() {
        return this;
    }
    @Override
    public StateInterface defineWinner() {
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
