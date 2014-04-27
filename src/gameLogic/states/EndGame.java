package gameLogic.states;

import gameLogic.Game;

public class EndGame extends StateAdapter {
    
    // Contructor
    public EndGame(Game game) {
        super(game);
    }

    @Override
    public StateInterface defineEndGame() {
        // Not implemented yet
        // Has to calculate score and if its end of game according to number of drawn cards
        return this;
    }
    
    
}
