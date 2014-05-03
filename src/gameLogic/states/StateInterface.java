package gameLogic.states;

// Os argumentos podem não ser os mais correctos

import java.util.ArrayList;

public interface StateInterface {
    public StateInterface defineGame(int n);
    public StateInterface defineWinner(ArrayList<Integer> bets);
    public StateInterface defineCard(int n);
    public StateInterface defineAction(int n);
    public StateInterface definePlaceArmy(int regionId);
    public StateInterface defineMoveByLand(int from,int to);
    public StateInterface defineMoveBySea();
    public StateInterface defineCity();
    public StateInterface defineNeutralize();
    public StateInterface defineJokers(ArrayList<Integer> jokers);
    
    public StateInterface placeArmyOrCity();
    public StateInterface moveArmy();
    public StateInterface neutralize();
    public StateInterface reDo();
    
    public StateInterface saveToFile();
    public StateInterface loadFromFile();

    public StateInterface defineEndGame();
    
    
}
