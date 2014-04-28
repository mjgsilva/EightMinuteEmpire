package gameLogic.states;

import gameLogic.Card;
import gameLogic.Game;
import java.util.Map;

public class MoveArmyByLand extends StateAdapter {

    private static int playsCounter = 0;
    
    public MoveArmyByLand(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineMoveByLand(int from, int to)
    {
        /* TODO -> CARD */
         return this;
         
    }
    
}
