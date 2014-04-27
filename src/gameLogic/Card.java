package gameLogic;

import java.util.Iterator;
import java.util.Map;

public class Card {
    private final int id;
    private final int typeOfResource;
    private final int numberOfResource;
    private final int fivePlayersCard;
    private final Map <Integer, Integer> actions;

    Card(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, Map<Integer,Integer>actions)
    {
        this.id = id;
        this.typeOfResource = typeOfResource;
        this.numberOfResource = numberOfResource;
        this.fivePlayersCard = fivePlayersCard;
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "ID: " + id + " \t" + getTypeOfResource() + " Qty:" + numberOfResource
                + "\tAct: " + getActionsExtended() + "\t\t" + getFivePlayersCardToString();
    }

    private String getTypeOfResource()
    {
        String type = "";

        switch(typeOfResource) {
            case 1 : type = "Jewelry"; break;
            case 2 : type = "Food"; break;
            case 3 : type = "Wood"; break;
            case 4 : type = "Iron"; break;
            case 5 : type = "tools"; break;
            case 6 : type = "Joker"; break;
        }

        return type;
    }

    private String getFivePlayersCardToString()
    {
        String fivePlayers;

        if(fivePlayersCard == 1)
            fivePlayers = "FP Card";
        else
            fivePlayers = "Std Card";

        return fivePlayers;
    }

    private String getActionsExtended()
    {
        String actionsText = new String();
        Iterator it = actions.entrySet().iterator();
        int counter = 0;
        
        while(it.hasNext())
        {            
            Map.Entry pairs = (Map.Entry)it.next();
            actionsText += getActionString(Integer.parseInt(pairs.getKey().toString()),Integer.parseInt(pairs.getValue().toString()));
            if(counter > 0)
                actionsText += " OR ";
            counter++;
            it.remove();
        }
        return actionsText;
    }
    
    private String getActionString(int actionIndex, int actionUnits)
    {
        String actionList = new String();

        switch(actionIndex)
        {
            case 1 : actionList = "Place Army"; break;
            case 2 : actionList = "Move Army by Land"; break;
            case 3 : actionList = "Move Army by Sea"; break;
            case 4 : actionList = "Build a City"; break;
            case 5 : actionList = "Neutralize Army"; break;
        }

        actionList += " | " + actionUnits + " Unit[s]";

        return actionList;
    }

    public int getFivePlayersCard() {
        return fivePlayersCard;
    }
}