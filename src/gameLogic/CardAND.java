package gameLogic;

import gameLogic.states.AND;
import gameLogic.states.StateInterface;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class CardAND extends Card implements Serializable  {

    public CardAND(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, Map<Integer, Integer> actions, int and) {
        super(id, typeOfResource, numberOfResource, fivePlayersCard, actions, and);
    }
    
    @Override
    protected String getActionsExtended()
    {
        String actionsText = new String();
        Iterator it = getActions().entrySet().iterator();
        int counter = 0;
        
        while(it.hasNext())
        {    
            if(counter > 0)
                actionsText += " AND ";
            Map.Entry pairs = (Map.Entry)it.next();
            actionsText += getActionString(Integer.parseInt(pairs.getKey().toString()),Integer.parseInt(pairs.getValue().toString()));

            counter++;
        }
        return actionsText;
    }

    @Override
    public StateInterface returnState(Game game) {
        return new AND(game);
    }
}
