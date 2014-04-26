package ui.text;

import gameLogic.*;
import gameLogic.states.*;

public class UIText {   
    Game game = new Game(); // Provisório. Não esquecer que depois é preciso ter opção de carregar de um ficheiro
    StateAdapter state;
    public void run() {
        
        while (!((state = game.getState()) instanceof EndGame)) {
            if (state instanceof PrepareGame)
                PrepareGame();
            else if (state instanceof Auction)
                Auction();
            else if (state instanceof PickCard)
                PickCard();
            else if (state instanceof DefineAction)
                DefineAction();
        }
    }

    private void PrepareGame() {
        // Implementar inputs
    }

    private void Auction() {
        // Implementar inputs
    }

    private void PickCard() {
        // Implementar inputs
    }

    private void DefineAction() {
        // Implementar inputs
    }
}
