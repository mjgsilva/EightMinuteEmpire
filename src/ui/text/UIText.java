package ui.text;

import gameLogic.*;
import gameLogic.states.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UIText {
    Game game = new Game(); // Provisório. Não esquecer que depois é preciso ter opção de carregar de um ficheiro
    StateInterface state;
    
    Scanner sc = new Scanner(System.in);
    
    public void run() {
        while (!((state = game.getState()) instanceof EndGame)) {
            
            if (state instanceof PrepareGame)
                PrepareGame();
            else if (state instanceof Auction)
                Auction();
            else if (state instanceof PickCard)
                PickCard();
            else if (state instanceof DefineAction)
                DefineAction();
        }
    }

    private void PrepareGame() {
        // Implementar inputs
        System.out.print("Number of players: ");
        game.defineGame(sc.nextInt());
        System.out.print("\n");
        // If its returned the same state, then there's invalid data
        if (game.getState() instanceof PrepareGame)
            System.out.println("Invalid number of players.");
    }

    private void Auction() {
        // Implementar inputs
        ArrayList<Player> list = game.getPlayers();
        ArrayList<Integer> bets = new ArrayList<>();
        for (Player aux : list) {
            System.out.print("Player " + aux.getId() + ", insert number of coins yout want to bet: ");
            bets.add(sc.nextInt());
            System.out.print("\n");
        }
        game.defineWinner(bets);
        // If its returned the same state, then there's invalid data
        if (game.getState() instanceof Auction)
            System.out.println("Invalid bet made by a player.");
        else
            System.out.println("Player " + game.getCurrentPlayer().getId() + " won the auction!");
    }

    private void PickCard() {
        // Implementar inputs
    }

    private void DefineAction() {
        // Implementar inputs
    }
}
