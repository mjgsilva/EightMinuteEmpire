package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Region;

public class PlaceNewArmy extends StateAdapter {

    public PlaceNewArmy(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface definePlaceArmy(int regionId)
    {
        Region t;
        Player p;
        int playerId;
        String playerColor;
        Card c;
        int numberOfMovements;
        int mainRegion;
        getGame().setErrorFlag(Boolean.FALSE);
        
        
        c = getGame().getCurrentPlayer().getLastCard();
        numberOfMovements = c.findActionNumberOfPlays(1);
        t = getGame().getMap().getRegionById(regionId);
        p = getGame().getCurrentPlayer();
        playerId = p.getId();
        playerColor = p.getColor();
        mainRegion = getGame().getMap().getMainRegion();
        
        if(numberOfMovements > 0)
        { 
            if(t != null)
            {
                //if(regionId == mainRegion || (regionId.checkCitiesOfPlayerOnRegion(p)))
                if((t.checkCitiesOfPlayerOnRegion(p)) || regionId == mainRegion)
                    t.addArmy(new Army(playerId, playerColor));
                else {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] Not a valid Region.");
                    return this;
                }
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
