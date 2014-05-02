package gameLogic;

import gameLogic.states.StateInterface;
import java.util.Iterator;
import java.util.Map;

public class Card {
    private final int id;
    private int typeOfResource;
    private final int numberOfResource;
    private final int fivePlayersCard;
    protected Map <Integer, Integer> actions;
    private final int andCard;


    // Constructor for not AND cards
    Card(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, Map<Integer,Integer>actions, int andCard)
    {
        this.id = id;
        this.typeOfResource = typeOfResource;
        this.numberOfResource = numberOfResource;
        this.fivePlayersCard = fivePlayersCard;
        this.actions = actions;
        this.andCard = andCard;
    }

    public Card(int i) {
        // To use contains and check for type of resource
        this.id = 0;
        this.typeOfResource = i;
        this.numberOfResource = 0;
        this.fivePlayersCard = 0;
        this.andCard = 0;
    }

    public Map<Integer, Integer> getActions() {
        return actions;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " \t[NR:" + numberOfResource + "|" + getFivePlayersCardToString() + "] " + getTypeOfResource()
                + "\tAct: " + getActionsExtended();
    }

    private String getTypeOfResource()
    {
        String type = new String();

        switch(typeOfResource) {
            case 1 : type = "Jewelry"; break;
            case 2 : type = "Food   "; break;
            case 3 : type = "Wood   "; break;
            case 4 : type = "Iron   "; break;
            case 5 : type = "Tools  "; break;
            case 6 : type = "Joker  "; break;
        }

        return type;
    }

    private String getFivePlayersCardToString()
    {
        String fivePlayers;

        if(fivePlayersCard == 1)
            fivePlayers = "5PC";
        else
            fivePlayers = "Std";

        return fivePlayers;
    }

    protected String getActionsExtended()
    {
        String actionsText = new String();
        Iterator it = actions.entrySet().iterator();
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
    
    public String getActionString(int actionIndex, int actionUnits)
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

    public int getId() {
        return id;
    }
    
    public int get2TypeOfResource() {
        return typeOfResource;
    }

    public StateInterface returnState(Game game) {
        return null;
    }
    
    public int findActionNumberOfPlays(int id)
    {
        int value = actions.get(id);  
        return value;
    }
    
    public void updateActionMovements(int id)
    {
        actions.put(id, findActionNumberOfPlays(id)-1);
    }

    
    // Equal and Hash code are only taking type of resource into account
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.typeOfResource;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.typeOfResource != other.typeOfResource) {
            return false;
        }
        return true;
    }
    
    public void setTypeOfResource(int typeOfResource) {
        this.typeOfResource = typeOfResource;
    }
}