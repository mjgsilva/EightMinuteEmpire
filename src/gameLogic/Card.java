package gameLogic;

public class Card {
    private int id;
    private int typeOfResource;
    private int numberOfResource;
    private int fivePlayersCard;
    private int[][] actions;

    Card(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, int[][] actions)
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
                + "\tAct: " + getActions() + "\t\t" + getFivePlayersCardToString();
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

    private String getActions()
    {
        String actionsList = new String();
        int length = actions.length;

        for(int i = 0; i < length; i++)
        {
            String tempAction = new String();

            switch(actions[i][0])
            {
                case 1 : tempAction = "Place Army"; break;
                case 2 : tempAction = "Move Army by Land"; break;
                case 3 : tempAction = "Move Army by Sea"; break;
                case 4 : tempAction = "Build a City"; break;
                case 5 : tempAction = "Neutralize Army"; break;
            }

            tempAction += " | " + actions[i][1] + " Unit[s]";

            if(length > 1 && i+1 != length)
                tempAction += " OR ";

            actionsList += tempAction;
        }
        return actionsList;
    }

    public int getFivePlayersCard() {
        return fivePlayersCard;
    }
}