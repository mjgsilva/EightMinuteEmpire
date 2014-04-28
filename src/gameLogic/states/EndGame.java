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
        
        // Add resources scores
        players = setResourcesScores(players);
        
        getGame().setPlayers(players);
        return this;
    }

    private ArrayList<Player> setResourcesScores(ArrayList<Player> players) {
        // Calculate resource score by player
        for (Player aux : players) {
            int scoreToAdd = 0;
            // Run every type of cards minus Joker cards
            for (int i = 1; i < 5; i++) {
                int n = aux.getResourceUnities(i);
                // Depending on the resource being calculated
                switch(i) {
                    // Jewlery
                    case 1:
                        if (n == 1)
                            scoreToAdd += 1;
                        else if (n == 2)
                            scoreToAdd += 4;
                        else if (n == 3)
                            scoreToAdd += 3;
                        else if (n>=5)
                            scoreToAdd += 5;
                        break;
                        // Iron
                    case 2:
                        if (n == 2 || n == 3)
                            scoreToAdd += 1;
                        else if (n == 4)
                            scoreToAdd += 2;
                        else if (n == 5)
                            scoreToAdd += 3;
                        else if (n>=6)
                            scoreToAdd += 5;
                        break;
                        // Wood
                    case 3:
                        if (n == 2 || n == 3)
                            scoreToAdd += 1;
                        else if (n == 4)
                            scoreToAdd += 1;
                        else if (n == 5)
                            scoreToAdd += 3;
                        else if (n>=6)
                            scoreToAdd += 5;
                        break;
                        // Food
                    case 4:
                        if (n == 3 || n == 4)
                            scoreToAdd += 1;
                        else if (n == 5 || n == 6)
                            scoreToAdd += 2;
                        else if (n == 7)
                            scoreToAdd += 3;
                        else if (n>=8)
                            scoreToAdd += 5;
                        break;
                        // Tool
                    case 5:
                        if (n == 2 || n == 3)
                            scoreToAdd += 1;
                        else if (n == 4 || n == 5)
                            scoreToAdd += 2;
                        else if (n == 6)
                            scoreToAdd += 3;
                        else if (n>=7)
                            scoreToAdd += 5;
                        break;
                    default:
                        // Do nothing
                        break;
                }
                // Add score to player
                aux.addScore(scoreToAdd);
                System.out.println("");
            }
        }
        
        return players;
    }
}