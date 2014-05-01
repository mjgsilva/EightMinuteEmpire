package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.Game;
import gameLogic.map.Region;
import java.util.Map;

public class MoveArmyByLand extends StateAdapter {
    
    public MoveArmyByLand(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineMoveByLand(int from, int to)
    {
        Region f;
        Region t;
        int playerId;
        String playerColor;
        int numberOfMovements;
        getGame().setErrorFlag(Boolean.FALSE);

        Card c = getGame().getCurrentPlayer().getLastCard();
        numberOfMovements = findActionNumberOfPlays(c);
        playerId = getGame().getCurrentPlayer().getId();
        playerColor = getGame().getCurrentPlayer().getColor();
        
        if(numberOfMovements > 0)
        { 
            f = getGame().getMap().getRegionById(from);
            t = getGame().getMap().getRegionById(to);
            
            if((f != null) && ((t != null)))
            {
                if(f.checkAdjacencyByLand(t.getId()))
                {
                    if(f.checkArmiesOfPlayersByRegion(playerId))
                    {
                        f.removeArmy(new Army(playerId, playerColor));
                        t.addArmy(new Army(playerId, playerColor));
                    } else {
                        getGame().setErrorFlag(Boolean.TRUE);
                        getGame().setErrorMsg("[ERROR] You don't have any army on region " + from + ".\n");
                        return this;
                    }
                } else {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] Region " + to + " is too far away.\nYou can only move to adjacent regions.\n");
                    return this;
                }
            } else { // PROVISORY
                getGame().setErrorFlag(Boolean.TRUE);
                getGame().setErrorMsg("[ERROR] You don't have any army on region " + from + ".\n");
                return this;
            }
        }
        
        numberOfMovements = updateActionMovements(c,numberOfMovements);
        if(numberOfMovements < 1)
            return new PickCard(getGame());
        else
            return this;
    }
    
    private int findActionNumberOfPlays(Card c)
    {
        return c.getActions().get(2);        
    }
    
    private int updateActionMovements(Card c, int numberOfMovements)
    {
        c.getActions().put(2, numberOfMovements-1);
        return --numberOfMovements;
    }
}
