package gameLogic.states;

// Os argumentos podem não ser os mais correctos

import java.util.ArrayList;

public interface StateInterface {
    public StateInterface defineGame(int n);
    public StateInterface defineWinner(ArrayList<Integer> bets);
    public StateInterface defineCard(int n);
    public StateInterface defineSelectedAction(int n);
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

    public StateInterface defineEndGame();
    
    
}
