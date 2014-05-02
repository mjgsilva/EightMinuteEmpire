package ui.text;

import gameLogic.*;
import gameLogic.map.GameMap;
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
        while (!((state = game.getState()) instanceof EndGame)) {
            if (state instanceof PrepareGame)
                PrepareGame();
            else if (state instanceof Auction)
                Auction();
            else if (state instanceof PickCard)
                PickCard();
            else if (state instanceof SelectAction) // ESTE É PARA SAIR
                SelectAction();
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
        if (game.getState() instanceof PrepareGame && game.getPreviousState() instanceof PrepareGame) {
            // Input number of players
            System.out.print("Number of players: ");
            game.defineGame(sc.nextInt());
            System.out.print("\n");
            
            // Error
            if (game.isErrorFlag()) {
                System.out.println(game.getErrorMsg());
                return;
            }
            
            // Show 6 cards
            System.out.println("-------- Cards --------\n" + game.getTableCardsAsString());
            System.out.println("-------- World Map --------\n" + game.getMapAsString());
        } else {
            // Transform joker cards into resource cards
            
            // "End Game"
            game.defineJokers();
            // Show endGame scores
            
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
        // This is the beginning of the turn, so we have to check for EndGame condition.
        // DO NOT FORGET TO IMPLEMENT SAVE GAME HERE.
        game.defineEndGame();
        if (game.getState() instanceof EndGame) {
            endGame();
            return;
        }
        
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
        
        if (game.getState() instanceof SelectAction) {
            System.out.println("\nInvalid move.");
            return;
        }
    }

    private void CardAND() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void PlaceNewArmy() {
        int regionId;
        Card c = game.getCurrentPlayer().getLastCard();
        System.out.println(game.getMapAsString());
        System.out.println(c.toString());
        System.out.println("------ [Region ID] To place the new army: ------\n");
        regionId = sc.nextInt();
        game.definePlaceArmy(regionId);
    }
    
    private void MoveArmyByLand() {
        int from;
        int to;
        
        Card c = game.getCurrentPlayer().getLastCard();
        System.out.println(game.getMapAsString());
        System.out.println(c.toString());
        System.out.println("------ [Region ID] From: ------\n");
        from = sc.nextInt();
        System.out.println("------ [Region ID] To: ------\n");
        to = sc.nextInt();
        game.defineMoveByLand(from,to);
        
        if (game.getState() instanceof SelectAction) {
            System.out.println("\nProblems!.");
            return;
        }
    }
    
    private void endGame() {
        game.defineEndGame();
        System.out.println("\nGame Over. Not implemented yet.");
        for (Player aux : game.getPlayers())
            System.out.println("Score " + aux.getIdAsString() + ": " + aux.getScore());
    }
}
