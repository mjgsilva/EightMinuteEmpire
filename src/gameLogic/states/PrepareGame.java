package gameLogic.states;

import gameLogic.Card;
import gameLogic.Deck;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Continent;
import gameLogic.map.GameMap;
import gameLogic.map.Region;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PrepareGame extends StateAdapter implements Serializable {
    public PrepareGame(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineGame(int n) {
        int value = 0;
        //getGame().setEndGameFlag(false);
        getGame().setErrorFlag(Boolean.FALSE);
        
        if (getGame().getEndGameFlag())
            return endGame();
        
        if (n == 0) {
            getGame().setExitFlag(true);
            return this;
        }
        
        // Preparation of map
        // Being made by default on Game class
        
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
                aux.setCoins(value);
            // Update Players list in Game
            getGame().setPlayers(temporaryList);
            // Place 3 armies for all players at initial region (id: 12)
            getGame().setMap(placeInitialArmies());
            // Update deck according to number of players
            if (n < 5) {
                Deck deck = getGame().getDeck();
                deck.remove5PlayersCard();
                getGame().setDeck(deck);
            }
            // Set 6 random table cards
            for (int i = 0; i < 6; i++)
                getGame().addTableCard(getGame().getCardFromDeck());
            
            // Set previous state
            getGame().setPreviousState(getGame().getState());
            
            return new Auction(getGame());
        } else {
            getGame().setErrorFlag(Boolean.TRUE);
            getGame().setErrorMsg("[ERROR] Number of players has to be between 2 and 5.\n");
            return this;
        }
    }

    private GameMap placeInitialArmies() {
        GameMap map = getGame().getMap();
        for (Player aux : getGame().getPlayers()){
            for (int i = 0; i < 3; i++) {
                if(aux.getArmies().size() > 0)
                    map.placeArmy(map.getMainRegion(), aux.getArmy());
            }
        }

        return map;
    }
    
    private StateInterface endGame() {
        // if players have jokers then initiate joker substate
        for (Player aux : getGame().getPlayers())
            for (Card auxCard : aux.getCards())
                if (auxCard.get2TypeOfResource() == 6) {
                    getGame().setCurrentPlayer(aux);
                    return new DefineJokers(getGame());
                }
        
        ArrayList <Player> players = getGame().getPlayers();
        ArrayList <Integer> playersTempScore = new ArrayList<>();
        
        ArrayList <Integer> playersTotalRegions = new ArrayList<>(); // For unties
        
        // Initialize playersTempScore with 0's
        for (Player player : players) {
            playersTempScore.add(0);
            
            playersTotalRegions.add(0);
        }
        
        // Calculate owners of Continentes and sum points to respective owner
        for (Continent auxContinent : getGame().getMap().getContinents()) {
            // Calculate owners of Regions and sum points to respective owner
            for (Region auxRegion : auxContinent.getRegions()) {
                int index = auxRegion.returnOwner(getGame().getPlayers().size()) - 1;
                // If owned
                if (index > -1) {
                    players.get(index).addScore(1);
                    playersTempScore.set(index, playersTempScore.get(index)+1);
                    
                    playersTotalRegions.set(index, playersTotalRegions.get(index)+1);
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
                // Do nothing.
            } else {
                // Add point to owner
                players.get(playersTempScore.indexOf(Collections.max(playersTempScore))).addScore(1);
            }
            // Reset playersTempScore to 0's
            for (int i = 0; i < playersTempScore.size(); i++) {
                playersTempScore.set(i, 0);
            }
        }
        
        // Add resources scores
        players = setResourcesScores(players);
        
        getGame().setPlayers(players);
        
        //Place winner at current player
        getGame().setCurrentPlayer(getWinner(playersTotalRegions));
        
        // Restart game
        getGame().setPreviousState(new PrepareGame(getGame()));
        
        getGame().setEndGameFlag(false);
        
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
                            scoreToAdd += 3;
                        else if (n == 3)
                            scoreToAdd += 4;
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
                        scoreToAdd += 0;
                        break;
                }
                // Add score to player
                aux.addScore(scoreToAdd);
                // Reset variable
                scoreToAdd = 0;
            }
        }
        
        return players;
    }

    private Player getWinner(ArrayList<Integer> playersTotalRegions) {
        Player max = getGame().getPlayers().get(0);
        
        // Get maximum score
        for (Player aux : getGame().getPlayers()) {
            if (aux.getScore() > max.getScore())
                max = aux;
        }
        
        // Untie if possible
        for (Player aux : getGame().getPlayers().subList(1, getGame().getPlayers().size())) {
            if (aux.getScore() == max.getScore())
                if (aux.getCoins() > max.getCoins()) {
                    max = aux;
                } else if (aux.getCoins() == max.getCoins())
                    if (playersTotalRegions.get(aux.getId()-1) > playersTotalRegions.get(max.getId()-1)) {
                        max = aux;
                    } else if (Objects.equals(playersTotalRegions.get(max.getId()-1), playersTotalRegions.get(aux.getId()-1)))
                        return null; // ends in tie
        }
        return max;
    }

    public class DefineJokers extends PrepareGame {

        public DefineJokers(Game game) {
            super(game);
        }

        @Override
        public StateInterface defineGame(int n) {
            if (n > 0 && n < 6) {
                for (Card aux : getGame().getCurrentPlayer().getCards())
                    if (aux.get2TypeOfResource() == 6)
                        aux.setTypeOfResource(n);
                //getGame().getCurrentPlayer().getCards().get(getGame().getCurrentPlayer().getCards().indexOf(new Card(6))).setTypeOfResource(n);
            }
            return new PrepareGame(getGame());
        }
    }
}