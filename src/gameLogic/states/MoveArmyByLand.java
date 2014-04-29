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
        int numberOfPlays;
        Region f;
        Region t;
        int playerId;
        String playerColor;
        
        Card c = getGame().getCurrentPlayer().getLastCard();
        Map.Entry pairs = (Map.Entry)c.getActions();
        numberOfPlays = Integer.parseInt(pairs.getValue().toString());
        playerId = getGame().getCurrentPlayer().getId();
        playerColor = getGame().getCurrentPlayer().getColor();
        
        if(numberOfPlays > 0)
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
        
        /* Remove -1 movement to the card */
        c.getActions().put(Integer.parseInt(pairs.getKey().toString()), numberOfPlays-1);
        if(numberOfPlays < 1)
            return new PickCard(getGame());
        else
            return new MoveArmyByLand(getGame());
    }
    
}
