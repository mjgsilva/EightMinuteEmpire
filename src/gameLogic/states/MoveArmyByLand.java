package gameLogic.states;

import gameLogic.Game;

public class MoveArmyByLand extends StateAdapter {

    public MoveArmyByLand(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineMoveByLand(int numberOfMovements)
    {
        return this;
    }
    
}
