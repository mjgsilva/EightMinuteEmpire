package ui.text;

import gameLogic.*;
import gameLogic.states.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class UIText {
    Game game = new Game();
    StateInterface state;
    //Deck d = new Deck();        
    Scanner sc = new Scanner(System.in);
//    private boolean exit = false;
    
    public void run() {
        while (!game.getExitFlag()) {
            state = game.getState();
            if (state instanceof PrepareGame)
                PrepareGame();
            else if (state instanceof Auction)
                Auction();
            else if (state instanceof PickCard)
                PickCard();
            else if (state instanceof OR)
                CardOR();
            else if (state instanceof AND)
                CardAND();
            else if (state instanceof MoveArmyByLand)
                MoveArmyByLand();
            else if (state instanceof PlaceNewArmy)
                PlaceNewArmy();
            else if (state instanceof MoveArmyBySea)
                MoveArmyBySea();
            else if (state instanceof BuildCity)
                BuildCity();
            else if (state instanceof NeutralizeArmy)
                NeutralizeArmy();
        }
    }
    
    private void PrepareGame() {
        if (!game.getEndGameFlag()) {
            // Input number of players
            System.out.println("Eight Minutes Empire - Computer Game Edition\n"
                    + "Enter 0 to exit.\n");
            
            System.out.print("To load previous game enter 1.\nNumber of players: ");
            int op = sc.nextInt();
            if (op == 1) {
                FileInputStream fis = null;
                ObjectInputStream in = null;
                try {
                  fis = new FileInputStream("eme.bin");
                  in = new ObjectInputStream(fis);
                  game = (Game) in.readObject();
                  in.close();
                } catch (Exception ex) {
                  //ex.printStackTrace();
                    System.out.println("Error loading file.");
                    return;
                }
            } else 
                game.defineGame(op);
            System.out.print("\n");
            
            // Error
            if (game.isErrorFlag()) {
                System.out.println(game.getErrorMsg());
                return;
            } else if (game.getEndGameFlag())
                return;
            
            if (op !=1) {
                // Show 6 cards
                System.out.println("-------- Cards --------\n" + game.getTableCardsAsString());
                System.out.println("-------- World Map --------\n" + game.getMapAsString());
            }
        }
        // "End Game"
        // Show endGame scores
        if (game.getEndGameFlag()) {
            game.defineGame(0);
            if (game.getState() instanceof PrepareGame.DefineJokers) {
                System.out.println("Player " + game.getCurrentPlayer().getIdAsString());
                System.out.println("Type of resource:\n"
                            + "1 - Jewelry\n"
                            + "2 - Food\n"
                            + "3 - Wood\n"
                            + "4 - Iron\n"
                            + "5 - Tools\n");
                game.defineGame(sc.nextInt());
                return;
            } else {
                if (game.getCurrentPlayer() != null)
                    System.out.println("\nGame Over. "
                        + "\nWinner is Player " + game.getCurrentPlayer().getIdAsString() + "!");
                else {
                    System.out.println("\nGame Over\nGame tied! No one wins!");
                }
                for (Player aux : game.getPlayers()) {
                    System.out.println("Score " + aux.getIdAsString() + ": " + aux.getScore());
                }
            }
            //game.defineGame(0);
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
    }

    private void PickCard() {
        // Input desired card to buy
        System.out.println("-------- Cards --------\n" + game.getTableCardsAsString());
        System.out.println("Player " + game.getCurrentPlayer().getIdAsString() + " turn" +
                "\nCoins: " + game.getCurrentPlayer().getCoins());
        System.out.print("\nOption 7 to save.\nPick card number to buy (According to displayed order): ");
        int op = sc.nextInt()-1;
        game.defineCard(op);
        
        if (game.getState() instanceof PickCard && game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
            return;
        }
        // Show players information
        if (op != 6) {
            System.out.println("\nBought card: " + game.getCurrentPlayer().getLastCard());
            System.out.println("Remaining coins: " + game.getCurrentPlayer().getCoins());
            System.out.println("Score: " + game.getCurrentPlayer().getScore());
        } else
            System.out.println("Game saved.");
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
        System.out.print("Insert Region ID\n0 to check\nOption: ");
        regionId = sc.nextInt();
        System.out.println("");
        game.defineAction(regionId);
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
    
    private void MoveArmyByLand() {
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
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
    
    private void MoveArmyBySea() {
        if (game.getState() instanceof MoveArmyBySea.InsertDestiny) {
            System.out.print("To (Region ID): ");        
        } else {
            System.out.println(game.getMapAsString());
            System.out.println(game.getCurrentPlayer().getLastCard().toString());
            System.out.print("First insert From Region, then To Region\n0 to check\n"
                    + "From (Region ID): ");
        }
        
        game.defineAction(sc.nextInt());
        System.out.println("");
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }

    private void BuildCity() {
        int regionId;
        Card c = game.getCurrentPlayer().getLastCard();
        System.out.println(game.getMapAsString());
        System.out.println(c.toString());
        System.out.print("Insert Region ID\n0 to check\nOption: ");
        regionId = sc.nextInt();
        System.out.println("");
        game.defineAction(regionId);
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        } else
            System.out.println(game.getMapAsString());
    }
    
    private void NeutralizeArmy() {
        if (game.getState() instanceof NeutralizeArmy.InsertPlayer) {
            System.out.print("Player ID: ");        
        } else {
            System.out.println(game.getMapAsString());
            System.out.println(game.getCurrentPlayer().getLastCard().toString());
            System.out.print("Insert Region ID:\n0 to check\n");
        }

        game.defineAction(sc.nextInt());
        System.out.println("");
        
        if (game.isErrorFlag()) {
            System.out.println(game.getErrorMsg());
        }
    }
}
