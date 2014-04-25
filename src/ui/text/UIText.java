package ui.text;

import gameLogic.*;
import gameLogic.states.*;

public class UIText {   
    Game game = new Game();
    StateAdapter state;
    public void run() {
        
        while (!((state = game.getState()) instanceof EndGame)) {
            
        }
    }
}
