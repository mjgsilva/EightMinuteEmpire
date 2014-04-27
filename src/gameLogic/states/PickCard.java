package gameLogic.states;

import gameLogic.Card;
import gameLogic.Game;
import java.util.ArrayList;

public class PickCard extends StateAdapter {
    // Contructor
    public PickCard(Game game) {
        super(game);
    }

    @Override
    public StateInterface defineEndGame() {
        // If all flags are true then it has reached the condition to end game
        ArrayList <Boolean> flags = new ArrayList<>();
        int n = 0;
        int nPlayers = getGame().getPlayers().size();
        
        for (int i = 0; i < nPlayers; i++)
            flags.add(false);
        
        switch(n) {
            case 2:
                n = 13;
                break;
            case 3:
                n = 10;
                break;
            case 4:
                n = 8;
                break;
            case 5:
                n = 7;
                break;
            default:
                n = 99;
                break;
        }
        
        for (int i = 0; i < getGame().getPlayers().size(); i++) {
            if (getGame().getPlayers().get(i).getCards().size() >= n)
                flags.set(i, true);
        }
        
        if (flags.contains(false)) {
            return this;
        } else
            return new EndGame(getGame());
    }

    @Override
    public StateInterface defineCard(int n) {
        if (n <= 6 && n > 0) {
            int cardCost = 0;
            switch(n+1) {
                case 1:
                    cardCost = 0;
                    break;
                case 2:
                    cardCost = 1;
                    break;
                case 3:
                    cardCost = 1;
                    break;
                case 4:
                    cardCost = 2;
                    break;
                case 5:
                    cardCost = 2;
                    break;
                case 6:
                    cardCost = 3;
                    break;
            }
            // If player doesn't have enough money
            if (getGame().getCurrentPlayer().getCoins() - cardCost < 0)
                return this;
            
            ArrayList <Card> cards = getGame().getTableCards();
            getGame().getCurrentPlayer().addCardBought(cards.get(n));
            cards.remove(n);
            getGame().setTableCards(cards);
            getGame().addTableCard(getGame().getCardFromDeck());
            
            // Remove coins
            getGame().getCurrentPlayer().useCoins(cardCost);
            
            return new DefineAction(getGame());
        } else {
            return this;
        }
        
    }
    
    
}
