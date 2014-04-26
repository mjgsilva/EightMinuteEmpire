package gameLogic;

import gameLogic.states.*;

public class Game {
    private StateAdapter state;
    public Game() {
        state = new EndGame();
    }

    public StateAdapter getState() {
        return state;
    }
}
