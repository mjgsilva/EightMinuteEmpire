package gameLogic.states;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Continent;
import gameLogic.map.Region;
import java.util.ArrayList;

public class EndGame extends StateAdapter {
    
    // Contructor
    public EndGame(Game game) {
        super(game);
    }

    /**
     * Just enters call this function if all players have drawn the require number
     * of cards to end game.
     *
     * @return this
     */
    @Override
    public StateInterface defineEndGame() {
        ArrayList <Player> players = getGame().getPlayers();
        
        for (Continent auxContinent : getGame().getMap().getContinents()) {
            for (Region auxRegion : auxContinent.getRegions()) {
                int index = auxRegion.returnOwner(getGame().getPlayers().size())-1;
                if (index != -1)
                    players.get(index).addScore(1);
            }
        }
        
        getGame().setPlayers(players);
        return this;
    }
}