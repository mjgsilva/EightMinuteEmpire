package gameLogic.states;

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
            if (getGame().isEndGameConditionMet()) {
                getGame().setEndGameFlag(true);
                return new PrepareGame(getGame());
            } else
                return new PickCard(getGame());
        }
        
        Region t;
        Player p;
        Card c;
        int numberOfMovements;
        getGame().setErrorFlag(Boolean.FALSE);
        
        
        c = getGame().getCurrentPlayer().getLastCard();
        numberOfMovements = c.findActionNumberOfPlays(4); // Type of action - Build city -> 4
        t = getGame().getMap().getRegionById(regionId);
        p = getGame().getCurrentPlayer();
        
        if(numberOfMovements >= 1)
        {
            if(t != null)
            {
                if(t.checkArmiesOfPlayerOnRegion(p))
                     t.addCity(new City(p.getId(), p.getColor()));
                else {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] You don't have any army on region " + regionId + ".\n");
                    getGame().setPreviousState(this);
                    return new BuildCity(getGame());
                }
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
//                if (getGame().isEndGameConditionMet()) {
//                    getGame().setEndGameFlag(true);
//                    return new PrepareGame(getGame());
//                } else {
//                    getGame().nextPlayer();
//                    return new PickCard(getGame());
//                }
                getGame().nextPlayer();
                getGame().setPreviousState(this);
                if (getGame().isEndGameConditionMet()) {
                    getGame().setEndGameFlag(true);
                    return new PrepareGame(getGame());
                } else
                    return new PickCard(getGame());
            }
            else
                return this;
    }
}
