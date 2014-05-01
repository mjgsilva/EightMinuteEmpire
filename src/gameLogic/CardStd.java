package gameLogic;

import java.util.Iterator;
import java.util.Map;

public class CardStd extends Card {

    public CardStd(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, Map<Integer, Integer> actions, int and) {
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
                actionsText += " OR ";
            Map.Entry pairs = (Map.Entry)it.next();
            actionsText += getActionString(Integer.parseInt(pairs.getKey().toString()),Integer.parseInt(pairs.getValue().toString()));

            counter++;
        }
        return actionsText;
    }
}
