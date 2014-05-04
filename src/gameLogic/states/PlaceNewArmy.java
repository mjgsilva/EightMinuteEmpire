package gameLogic.states;

import gameLogic.Card;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Region;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class PlaceNewArmy extends StateAdapter implements Serializable {

    public PlaceNewArmy(Game game) {
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
            } else {
                return new PickCard(getGame());
            }
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
                if((t.checkCitiesOfPlayerOnRegion(p)) || regionId == mainRegion)
                {
                    t.addArmy(p.getArmy());
                }
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
            if (numberOfMovements > 1)
                return new PlaceNewArmy(getGame());
        }
            
        if(c.findActionNumberOfPlays(1) <= 0) {
                if (getGame().isEndGameConditionMet()) {
                    getGame().nextPlayer();
                    getGame().setPreviousState(this);
                    getGame().setEndGameFlag(true);
                    return new PrepareGame(getGame());
                }else if(getGame().getPreviousState() instanceof AND) {
                    // In case of a And card do:
                    Map<Integer, Integer> actions = c.getActions();
                    Iterator it = actions.entrySet().iterator();
                    int index = 1;
                    int action = 0;

                    // Breaks out if number of moves is bigger than 0
                    while(it.hasNext()) {
                        Map.Entry pairs = (Map.Entry)it.next();
                        action = Integer.parseInt(pairs.getKey().toString());
                        if (Integer.parseInt(pairs.getValue().toString()) > 0)
                            break;
                    }

                    // Sets himself as previous state on game
                    getGame().setPreviousState(this);

                    // Returns the remainig action
                    switch(action) {
                        case 1 : return new PlaceNewArmy(getGame());
                        case 2 : return new MoveArmyByLand(getGame());
                        case 3 : return new MoveArmyBySea(getGame());
                        case 4 : return new BuildCity(getGame());
                        case 5 : return new NeutralizeArmy(getGame());
                        default: return this;
                    }
                } else {
                    getGame().nextPlayer();
                    // Sets himself as previous state on game
                    getGame().setPreviousState(this);
                    if (getGame().isEndGameConditionMet()) {
                        getGame().setEndGameFlag(true);
                        return new PrepareGame(getGame());
                    } else
                        return new PickCard(getGame());
                }
            }
            else
                return this;
    }
}
