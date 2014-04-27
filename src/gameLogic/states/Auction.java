package gameLogic.states;

import gameLogic.Game;
import java.util.ArrayList;
import java.util.Collections;

public class Auction extends StateAdapter {

    public Auction(Game game) {
        super(game);
    }

    @Override
    public StateInterface defineWinner(ArrayList<Integer> bets) {
        if (bets.size() == getGame().getPlayers().size()) {
            // In case of invalid bet return same state
            for (int i = 0; i < bets.size(); i++) {
                if (bets.get(i) < 0 || bets.get(i) > getGame().getPlayers().get(i).getCoins())
                    return this;
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
            return new PickCard(getGame());
        } else
            return this;
    }
}
