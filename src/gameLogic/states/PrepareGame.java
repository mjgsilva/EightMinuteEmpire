package gameLogic.states;

import gameLogic.Deck;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.GameMap;
import java.util.ArrayList;

public class PrepareGame extends StateAdapter {
    public PrepareGame(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineGame(int n) {
        int value = 0;
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
        } else
            return this;
    }

    private GameMap placeInitialArmies() {
        GameMap map = getGame().getMap();
        for (Player aux : getGame().getPlayers()){
            for (int i = 0; i < 3; i++) {
                if(aux.getArmies().size() > 0)
                    map.placeArmy(12, aux.getArmy());
            }
        }
        
        // For tests - tested and it works!
        /*map.placeArmy(12, getGame().getPlayers().get(0).getArmy());
        map.placeArmy(16, getGame().getPlayers().get(0).getArmy());
        map.placeArmy(21, getGame().getPlayers().get(0).getArmy());
            
        map.placeArmy(1, getGame().getPlayers().get(1).getArmy());
        map.placeArmy(22, getGame().getPlayers().get(1).getArmy());
        map.placeArmy(25, getGame().getPlayers().get(1).getArmy());*/
        
        return map;
    }
}
