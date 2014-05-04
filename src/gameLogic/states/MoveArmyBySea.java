package gameLogic.states;

import gameLogic.Army;
import gameLogic.Card;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Region;
import java.io.Serializable;

public class MoveArmyBySea extends StateAdapter implements Serializable {
    private int from = 0;
    private int to = 0;
    
    public MoveArmyBySea(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineAction(int action) {
        getGame().setErrorFlag(Boolean.FALSE);
        
        // Check action
        if (action == 0) {
            getGame().nextPlayer();
            getGame().setPreviousState(this);
            if (getGame().isEndGameConditionMet()) {
                getGame().setEndGameFlag(true);
                return new PrepareGame(getGame());
            } else
                return new PickCard(getGame());
        }
        
        // Inserted only from Region
        if (from == 0) {
            from = action;
            return new InsertDestiny(getGame());
        }
        return this;
    }
    
    public class InsertDestiny extends MoveArmyBySea {

        public InsertDestiny(Game game) {
            super(game);
        }

        @Override
        public StateInterface defineAction(int action) {
            
            Region f;
            Region t;
            int playerId;
            String playerColor;
            int numberOfMovements;
            Player p;
            
            getGame().setErrorFlag(Boolean.FALSE);
            
            Card c = getGame().getCurrentPlayer().getLastCard();
            numberOfMovements = c.findActionNumberOfPlays(3);

            p = getGame().getCurrentPlayer();
            playerId = p.getId();
            playerColor = p.getColor();

            if(numberOfMovements > 0)
            {
                to = action;
                f = getGame().getMap().getRegionById(from);
                t = getGame().getMap().getRegionById(to);

                if((f != null) && ((t != null)))
                {
                        if(f.checkAdjacencyBySea(t.getId()))
                        {
                            if(f.checkArmiesOfPlayerOnRegion(p))
                            {
                                f.removeArmy(new Army(playerId, playerColor));
                                t.addArmy(new Army(playerId, playerColor));
                            } else {
                                getGame().setErrorFlag(Boolean.TRUE);
                                getGame().setErrorMsg("[ERROR] You don't have any army on region " + from + ".\n");
                                getGame().setPreviousState(this);
                                return new MoveArmyBySea(getGame());
                            }
                        } else {
                            getGame().setErrorFlag(Boolean.TRUE);
                            getGame().setErrorMsg("[ERROR] Region " + to + " is too far away.\nYou can only move to adjacent regions.\n");
                            to = 0;
                            return this;
                        }
                } else {
                    getGame().setErrorFlag(Boolean.TRUE);
                    getGame().setErrorMsg("[ERROR] Invalid region(s).\n");
                    return new MoveArmyBySea(getGame());
                }
                c.updateActionMovements(3);
                if (numberOfMovements > 1) {
                    getGame().setPreviousState(this);
                    return new MoveArmyBySea(getGame());
                }
            }
        
            if(c.findActionNumberOfPlays(3) <=  0) {
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
}
