package gameLogic;

import gameLogic.states.BuildCity;
import gameLogic.states.MoveArmyByLand;
import gameLogic.states.MoveArmyBySea;
import gameLogic.states.NeutralizeArmy;
import gameLogic.states.PlaceNewArmy;
import gameLogic.states.StateInterface;
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
    
    /*
    1 - Place Army
    2 - Move Army By Land
    3 - Move Army By Sea (Crossover between Sea and Land)
    4 - Build City
    5 - Neutralize Army
     */
    @Override
    public StateInterface returnState(Game game) {
        Iterator it = actions.entrySet().iterator();
        Map.Entry pairs = (Map.Entry)it.next();
        
        switch(Integer.parseInt(pairs.getKey().toString())){
            case 1:
                return new PlaceNewArmy(game);
            case 2:
                return new MoveArmyByLand(game);
            case 3:
                return new MoveArmyBySea(game);
            case 4:
                return new BuildCity(game);
            case 5:
                return new NeutralizeArmy(game);
            default:
                return super.returnState(game);
        }
    }
}

