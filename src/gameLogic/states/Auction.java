package gameLogic.states;

import gameLogic.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Auction extends StateAdapter implements Serializable  {

    public Auction(Game game) {
        super(game);
    }

    @Override
    public StateInterface defineWinner(ArrayList<Integer> bets) {
        getGame().setErrorFlag(Boolean.FALSE);
        
        if (bets.size() == getGame().getPlayers().size()) {
            for (int i = 0; i < bets.size(); i++) {
                if (bets.get(i) < 0 || bets.get(i) > getGame().getPlayers().get(i).getCoins()) {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] Player " + getGame().getPlayerIdAsString(i) + " made an invalid bet.\n");
                    return this;
                }
            }
            // Get highest bet
            int max = Collections.max(bets);
            int index = bets.indexOf(max); 
            // If draw then random
            if (bets.indexOf(max) != bets.lastIndexOf(max)) {
                double randomNumber = Math.random();
                if (randomNumber > 0.5)
                    index = bets.indexOf(max);
                else
                    index = bets.lastIndexOf(max);
            }
            
            getGame().setCurrentPlayer(getGame().getPlayers().get(index));
            getGame().getCurrentPlayer().useCoins(bets.get(index));
            
            // Set previous state
            getGame().setPreviousState(getGame().getState());
            
            return new PickCard(getGame());
        } else {
            getGame().setErrorFlag(Boolean.TRUE);
            getGame().setErrorMsg("[ERROR] Number of bets isn't the same as number of players.\n");
            return this;
        }
    }
}
