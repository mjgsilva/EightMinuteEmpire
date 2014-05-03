package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.City;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Region;

public class BuildCity extends StateAdapter{

    public BuildCity(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineAction(int regionId)
    {
        if (regionId == 0) {
            getGame().nextPlayer();
            getGame().setPreviousState(this);
            return new PickCard(getGame());
        }
        
        Region t;
        Player p;
        int playerId;
        String playerColor;
        Card c;
        int numberOfMovements;
        int mainRegion;
        getGame().setErrorFlag(Boolean.FALSE);
        
        
        c = getGame().getCurrentPlayer().getLastCard();
        numberOfMovements = c.findActionNumberOfPlays(4); // Type of action - Build city -> 4
        t = getGame().getMap().getRegionById(regionId);
        p = getGame().getCurrentPlayer();
        playerId = p.getId();
        playerColor = p.getColor();
        mainRegion = getGame().getMap().getMainRegion();
        
        if(numberOfMovements >= 1)
        {
            if(t != null)
            {
                t.addCity(new City(playerId, p.getColor()));
            } else {
                getGame().setErrorFlag(Boolean.TRUE);
                getGame().setErrorMsg("[ERROR] Region " + regionId + " does not exist.\n");
                return this;
            }
            c.updateActionMovements(4);
            if (numberOfMovements > 1)
                return new BuildCity(getGame());
        }
            
        if(c.findActionNumberOfPlays(4) <= 0) {
                if (getGame().isEndGameConditionMet()) {
                    getGame().setEndGameFlag(true);
                    return new PrepareGame(getGame());
                } else {
                    getGame().nextPlayer();
                    return new PickCard(getGame());
                }
            }
            else
                return this;
    }
}
