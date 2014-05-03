package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Region;

public class NeutralizeArmy extends StateAdapter {

    private int regionId = 0;
    private int playerId = 0;
            
    public NeutralizeArmy(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineAction(int action) {
        getGame().setErrorFlag(Boolean.FALSE);
        
        // Check action
        if (action == 0) {
            getGame().nextPlayer();
            return new PickCard(getGame()); 
        }
        
        // Inserted only from Region
        if (regionId == 0) {
            regionId = action;
            return new InsertPlayer(getGame());
        }
        return this;
    }
    
    public class InsertPlayer extends NeutralizeArmy {

        public InsertPlayer(Game game) {
            super(game);
        }

        @Override
        public StateInterface defineAction(int action) {
            Region target;
            Player p;
            Army a;
            int numberOfMovements;
            
            getGame().setErrorFlag(Boolean.FALSE);
            Card c = getGame().getCurrentPlayer().getLastCard();
            numberOfMovements = c.findActionNumberOfPlays(5);

            if(numberOfMovements > 0)
            {
                playerId = action;
                target = getGame().getMap().getRegionById(regionId);

                if(target != null)
                {
                    p = getGame().getPlayerById(playerId);
                    if(p != null)
                    {
                        a = new Army(p.getId(),p.getColor());
                        if(target.checkArmiesOfPlayerOnRegion(p))
                        {
                            target.removeArmy(a);
                            p.addArmy(a);
                        } else {
                            getGame().setErrorFlag(Boolean.TRUE);
                            getGame().setErrorMsg("[ERROR] Player " + playerId + " haven't any army on region " + regionId + ".\n");
                            getGame().setPreviousState(this);
                            return new NeutralizeArmy(getGame());
                        }
                    } else {
                        getGame().setErrorFlag(Boolean.TRUE);
                        getGame().setErrorMsg("[ERROR] Player " + playerId + " does not exist " + regionId + ".\n");
                        getGame().setPreviousState(this);
                        return new NeutralizeArmy(getGame());
                    }
                } else {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] Region " + regionId + "doesn't exist.\n");
                    playerId = 0;
                    return this;
                }
                
                c.updateActionMovements(5);
                if (numberOfMovements > 1) {
                    getGame().setPreviousState(this);
                    return new NeutralizeArmy(getGame());
                }
            }
            
            if(c.findActionNumberOfPlays(5) <=  0) {
                if (getGame().isEndGameConditionMet()) {
                    getGame().setEndGameFlag(true);
                    getGame().setPreviousState(this);
                    return new PrepareGame(getGame());
                } else {
                    getGame().nextPlayer();
                    getGame().setPreviousState(this);
                    return new PickCard(getGame());
                }
            } else
                return this;
        }
    }
}
