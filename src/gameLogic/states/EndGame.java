package gameLogic.states;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Continent;
import gameLogic.map.Region;
import java.util.ArrayList;
import java.util.Collections;

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
        ArrayList <Integer> playersTempScore = new ArrayList<>();
        
        // Initialize playersTempScore with 0's
        for (Player player : players) {
            playersTempScore.add(0);
        }
        
        // Calculate owners of Continentes and sum points to respective owner
        for (Continent auxContinent : getGame().getMap().getContinents()) {
            // Calculate owners of Regions and sum points to respective owner
            for (Region auxRegion : auxContinent.getRegions()) {
                int index = auxRegion.returnOwner(getGame().getPlayers().size()) - 1;
                // If owned
                if (index > -1) {
                    players.get(index).addScore(1);
                    playersTempScore.set(index, 1);
                }
            }
            
            // Check for draws
            int flag = 0;
            for (int i = 0; i < players.size(); i++) {
                if (playersTempScore.get(i).equals(Collections.max(playersTempScore)))
                    flag++;
            }
            // Minor or equal 0 isn't necessary but you never can't be too carefull
            if (Collections.max(playersTempScore) <= 0 || flag >= 2) {
                // Do nothing
            } else {
                // Add point to owner
                players.get(playersTempScore.indexOf(Collections.max(playersTempScore))).addScore(1);
            }
            // Reset playersTempScore to 0's
            for (Player player : players) {
                playersTempScore.add(0);
            }
        }
        
        getGame().setPlayers(players);
        return this;
    }
}