package ui.text;

import gameLogic.*;
import gameLogic.states.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class UIText {
    Game game = new Game(); // Provisório. Não esquecer que depois é preciso ter opção de carregar de um ficheiro
    StateInterface state;
    Deck d = new Deck();        
    Scanner sc = new Scanner(System.in);
    
    public void run() {
        while (!game.getEndGameFlag()) {
            state = game.getState();
            if (state instanceof PrepareGame)
                PrepareGame();
            else if (state instanceof Auction)
                Auction();
            else if (state instanceof PickCard)
                PickCard();
            //else if (state instanceof SelectAction) // ESTE É PARA SAIR
              //  SelectAction();
            else if (state instanceof OR)
                CardOR();
            else if (state instanceof AND)
                CardAND();
            else if (state instanceof MoveArmyByLand)
                MoveArmyByLand();
            else if (state instanceof PlaceNewArmy)
                PlaceNewArmy();            
        }
    }
    
    private void PrepareGame() {
        if (!game.getEndGameFlag()) {
            // Input number of players
            System.out.println("Eight Minutes Empire - Computer Game Edition\n"
                    + "Enter 0 to exit.\n");
            
            System.out.print("Number of players: ");
            game.defineGame(sc.nextInt());
            System.out.print("\n");
            
            // Error
            if (game.isErrorFlag()) {
                System.out.println(game.getErrorMsg());
                return;
            } else if (game.getEndGameFlag())
                return;
            
            // Show 6 cards
            System.out.println("-------- Cards --------\n" + game.getTableCardsAsString());
            System.out.println("-------- World Map --------\n" + game.getMapAsString());
        } else {
            // Transform jokers into resource cards
            /*
            Type:
            1 - Jewelry
            2 - Food
            3 - Wood
            4 - Iron
            5 - Tools
            6 - Joker
            */
            if (!game.isErrorFlag()) {
                if (game.getCurrentPlayer().getCards().contains(new Card(6))) {
                    ArrayList <Integer> jokersNewType = new ArrayList<>();
                    System.out.println("Type of resource:\n"
                            + "1 - Jewelry\n"
                            + "2 - Food\n"
                            + "3 - Wood\n"
                            + "4 - Iron\n"
                            + "5 - Tools\n");
                    for (Card aux : game.getCurrentPlayer().getCards()) {
                        if (aux.equals(new Card(6))) {
                            System.out.print("Choose one type of resource: ");
                            jokersNewType.add(sc.nextInt());
                        }
                    }
                    game.defineJokers(jokersNewType);
                    
                    if (!game.getEndGameFlag())
                        return;
                } else {
                    System.out.println("Player " + game.getCurrentPlayer().getIdAsString() + " doesn't have any joker cards.\n");
                    return;
                }
            }
            
        }
        // "End Game"
        // Show endGame scores
        if (game.getEndGameFlag()) {
            System.out.println("\nGame Over. Not implemented yet.");
            for (Player aux : game.getPlayers())
                System.out.println("Score " + aux.getIdAsString() + ": " + aux.getScore());
        }
    }
    
    private void Auction() {
        // Input offers to determine first player to play
        ArrayList<Player> list = game.getPlayers();
        ArrayList<Integer> bets = new ArrayList<>();
        for (Player aux : list) {
            // This is supposed to be in secret, but IDE console doesn't support methods to do this....
            System.out.print("Player " + aux.getIdAsString() + ", insert number of coins yout want to bet: ");
            bets.add(sc.nextInt());
            System.out.print("\n");
        }
        System.out.println("\n");
        // Show offers made by players
        System.out.println("-------- Offers --------");
        for (int i = 0; i < bets.size(); i++)
            System.out.println("Player " + game.getPlayerIdAsString(i) + " : " + bets.get(i) + " coins");
        game.defineWinner(bets);
        // If same state is returned, then there's invalid data
        if (game.getState() instanceof Auction && game.isErrorFlag())
            System.out.println(game.getErrorMsg());
        else
            System.out.println("\nPlayer " + game.getCurrentPlayer().getIdAsString() + " won the auction!"
                    + "\nAnd is the first player to play.\n");
        //System.out.println("Remaining coins: " + game.getCurrentPlayer().getCoins());
    }

    private void PickCard() {
//        // This is the beginning of the turn, so we have to check for EndGame condition.
//        // DO NOT FORGET TO IMPLEMENT SAVE GAME HERE.
//        game.defineEndGame();
//        if (game.getState() instanceof EndGame) {
//            endGame();
//            return;
//        }
        
        // Input desired card to buy
        System.out.println("-------- Cards --------\n" + game.getTableCardsAsString());
        System.out.println("Player " + game.getCurrentPlayer().getIdAsString() + " turn" +
                "\nCoins: " + game.getCurrentPlayer().getCoins());
        System.out.println("Score: " + game.getCurrentPlayer().getScore());
        System.out.print("Pick card number to buy (According to displayed order): ");
        game.defineCard(sc.nextInt()-1);
        
        if (game.getState() instanceof PickCard && game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
            return;
        }
        // Show players information
        System.out.println("\nBought card: " + game.getCurrentPlayer().getLastCard());
        System.out.println("Remaining coins: " + game.getCurrentPlayer().getCoins());
        System.out.println("Score: " + game.getCurrentPlayer().getScore());
    }

    // Delete this function
    private void SelectAction() {
        Card c = game.getCurrentPlayer().getLastCard();
        Map<Integer, Integer> actions = c.getActions();
        Iterator it = actions.entrySet().iterator();
        int index = 0;
        
        System.out.println("------ What's your move? ------\n");
        System.out.println((index+1) + " - Check");
        while(it.hasNext())
        {   
            index++;
            String output = new String();         
            Map.Entry pairs = (Map.Entry)it.next();
                        System.out.println((index+1) + " - " + c.getActionString(Integer.parseInt(pairs.getKey().toString()), Integer.parseInt(pairs.getValue().toString())));
        }
        game.defineAction(sc.nextInt());
        
        if (game.getState() instanceof SelectAction) {
            System.out.println("\nInvalid move.");
            return;
        }
    }

    private void CardOR() {
        Card c = game.getCurrentPlayer().getLastCard();
        Map<Integer, Integer> actions = c.getActions();
        Iterator it = actions.entrySet().iterator();
        int index = 0;
        
        System.out.println("------ Pick one action ------\n");
        System.out.println((index+1) + " - Check");
        while(it.hasNext())
        {   
            index++;
            String output = new String();         
            Map.Entry pairs = (Map.Entry)it.next();
                        System.out.println((index+1) + " - " + c.getActionString(Integer.parseInt(pairs.getKey().toString()), Integer.parseInt(pairs.getValue().toString())));
        }
        game.defineCard(sc.nextInt());
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }

    private void CardAND() {
        Card c = game.getCurrentPlayer().getLastCard();
        Map<Integer, Integer> actions = c.getActions();
        Iterator it = actions.entrySet().iterator();
        int index = 0;
        
        System.out.println("------ Pick one action ------\n");
        System.out.println((index+1) + " - Check");
        while(it.hasNext())
        {
            index++;
            String output = new String();         
            Map.Entry pairs = (Map.Entry)it.next();
                        System.out.println((index+1) + " - " + c.getActionString(Integer.parseInt(pairs.getKey().toString()), Integer.parseInt(pairs.getValue().toString())));
        }
        game.defineCard(sc.nextInt());
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
    
    private void PlaceNewArmy() {
        int regionId;
        Card c = game.getCurrentPlayer().getLastCard();
        System.out.println(game.getMapAsString());
        System.out.println(c.toString());
        System.out.print("Insert Region ID\n0to check\nOption: ");
        regionId = sc.nextInt();
        System.out.println("");
        game.defineAction(regionId);
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
    
    private void MoveArmyByLand() {
        //int from;
        //int to;
        
        /*Card c = game.getCurrentPlayer().getLastCard();
        
        System.out.println(game.getMapAsString());
        System.out.println(game.getCurrentPlayer().getLastCard().toString());*/
        
        if (game.getState() instanceof MoveArmyByLand.InsertDestiny) {
            System.out.print("To (Region ID): ");        
        } else {
            System.out.println(game.getMapAsString());
            System.out.println(game.getCurrentPlayer().getLastCard().toString());
            System.out.print("First insert From Region, then To Region\n0 to check\n"
                    + "From (Region ID): ");
        }
        
        game.defineAction(sc.nextInt());
        System.out.println("");
        /*
        System.out.print("From (Region ID):");
        from = sc.nextInt();
        System.out.print("To (Region ID): ");
        to = sc.nextInt();
        game.defineMoveByLand(from,to);*/
        //System.out.println("");
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
}
