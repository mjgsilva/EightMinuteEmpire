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
                    } else
                        return this;
                } else
                    return this;
            } else 
                return this;
        }
        
        updateActionMovements(c,numberOfMovements);
        if(numberOfMovements < 1)
            return new PickCard(getGame());
        else
            return this;
    }
    
    private int findActionNumberOfPlays(Card c)
    {
        return c.getActions().get(2);        
    }
    
    private void updateActionMovements(Card c, int numberOfMovements)
    {
        c.getActions().put(2, numberOfMovements-1);
    }
}
