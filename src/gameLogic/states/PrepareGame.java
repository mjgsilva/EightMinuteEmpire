package gameLogic.states;

import gameLogic.Game;
import gameLogic.Player;
import java.util.ArrayList;

public class PrepareGame extends StateAdapter {
    public PrepareGame(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineGame(int n) {
        int value;
        
        // Number of players must be between 2 and 5
        if (n<=5 && n>=2) {
            ArrayList<Player> temporaryList = new ArrayList<>();
            
            for (int i = 0; i < n; i++)
                temporaryList.add(new Player(i+1));
            
            // Determines number of coins to distribute between players
            switch(n) {
                case 2:
                    value = 14;
                    break;
                case 3:
                    value = 11;
                    break;
                case 4:
                    value = 9;
                    break;
                case 5:
                    value = 8;
                    break;
            }
            // Distribute coins
            for (Player aux : temporaryList)
                aux.setCoins(n);
            // Update Players list in Game
            getGame().setPlayers(temporaryList);
            
            return new Auction(getGame());
        } else
            return this;
    }
    
    
}
