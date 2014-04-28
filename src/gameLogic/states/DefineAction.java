package gameLogic.states;

import gameLogic.Card;
import gameLogic.Game;
import java.util.Iterator;
import java.util.Map;

public class DefineAction extends StateAdapter {

    public DefineAction(Game game) {
        super(game);
    }
    
    @Override
    public StateInterface defineSelectedAction(int n)
    {
        Card c = getGame().getCurrentPlayer().getLastCard();
        int maximumSize = c.getActions().size();
        
        if(n < 1 || n > maximumSize + 1)
            return this;
        else
            if(n == 1) {
                int indexOfCurrentPlayer = getGame().getPlayers().indexOf(getGame().getCurrentPlayer());
                if (indexOfCurrentPlayer+1 == getGame().getPlayers().size())
                    indexOfCurrentPlayer = 0;
                else
                    indexOfCurrentPlayer++;
                getGame().setCurrentPlayer(getGame().getPlayers().get(indexOfCurrentPlayer));
               return new PickCard(getGame());
            } else
            {
                Map<Integer, Integer> actions = c.getActions();
                Iterator it = actions.entrySet().iterator();
                int index = 1;
                int action = 0; /*must be initialized*/
                int numberOfPlays;
                
                while(it.hasNext())
                {   
                    if(index >= n)
                        break;
                
                    Map.Entry pairs = (Map.Entry)it.next();
                    action = Integer.parseInt(pairs.getKey().toString());
                    numberOfPlays = Integer.parseInt(pairs.getValue().toString());
                    index++;
                }
                
                switch(action)
                {
                    case 1 : System.out.println("Place"); break;
                    case 2 : System.out.println("Move Land"); break;
                    case 3 : System.out.println("Move Sea"); break;
                    case 4 : System.out.println("Build Cityy"); break;
                }
            }
        return this;
    }
}
