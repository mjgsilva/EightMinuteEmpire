package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.Game;
import gameLogic.map.Region;

public class PlaceNewArmy extends StateAdapter {

    public PlaceNewArmy(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface definePlaceArmy(int regionId)
    {
        Region t;
        int playerId;
        String playerColor;
        Card c;
        int numberOfMovements;
        getGame().setErrorFlag(Boolean.FALSE);
        
        
        c = getGame().getCurrentPlayer().getLastCard();
        numberOfMovements = c.findActionNumberOfPlays(1);
        t = getGame().getMap().getRegionById(regionId);
        playerId = getGame().getCurrentPlayer().getId();
        playerColor = getGame().getCurrentPlayer().getColor();
        
        if(numberOfMovements > 0)
        { 
            if(t != null)
            {
                t.addArmy(new Army(playerId, playerColor));
            } else {
                getGame().setErrorFlag(Boolean.TRUE);
                getGame().setErrorMsg("[ERROR] Region " + regionId + " does not exist.\n");
                return this;
            }
            c.updateActionMovements(1);
        }
            
        if(numberOfMovements <= 1)
            return new PlaceNewArmy(getGame());
        else
            return this;
    }
}
