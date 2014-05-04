package gameLogic.states;

import java.io.Serializable;
import java.util.ArrayList;

public interface StateInterface extends Serializable {
    public StateInterface defineGame(int n);
    public StateInterface defineWinner(ArrayList<Integer> bets);
    public StateInterface defineCard(int n);
    public StateInterface defineAction(int n);  
}
