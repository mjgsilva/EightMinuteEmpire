package gameLogic.states;

// Os argumentos podem não ser os mais correctos
public interface StateInterface {
    public StateInterface defineGame();
    public StateInterface defineWinner();
    public StateInterface defineCard();
    public StateInterface defineArmy();
    public StateInterface defineMoveByLand();
    public StateInterface defineMoveBySea();
    public StateInterface defineCity();
    public StateInterface defineNeutralize();
    
    public StateInterface placeArmyOrCity();
    public StateInterface moveArmy();
    public StateInterface neutralize();
    public StateInterface reDo();
    public StateInterface newArmyThenNeutralize();
    
    public StateInterface saveToFile();
    public StateInterface loadFromFile();
    
    
}
