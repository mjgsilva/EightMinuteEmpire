package gameLogic.states;

import gameLogic.Card;
import gameLogic.Game;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class PickCard extends StateAdapter implements Serializable {
    // Contructor
    public PickCard(Game game) {
        super(game);
    }

    @Override
    public StateInterface defineCard(int n) {
        getGame().setErrorFlag(Boolean.FALSE);
        
        if (n < 7 && n >= 0) {
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
                case 7:
                    saveToFile(getGame());
                    return this;
            }
            // If player doesn't have enough money
            if (getGame().getCurrentPlayer().getCoins() - cardCost < 0) {
                getGame().setErrorFlag(Boolean.TRUE);
                getGame().setErrorMsg("[ERROR] Player " + getGame().getCurrentPlayer().getIdAsString() +
                        " doesn't have enough coins.\n");
                return this;
            }
            
            ArrayList <Card> cards = getGame().getTableCards();
            getGame().getCurrentPlayer().addCardBought(cards.get(n));
            cards.remove(n);
            getGame().setTableCards(cards);
            getGame().addTableCard(getGame().getCardFromDeck());
            
            // Remove coins
            getGame().getCurrentPlayer().useCoins(cardCost);
            
            // Set previous state
            getGame().setPreviousState(getGame().getState());
            
            //return new SelectAction(getGame());
            // polymorhic return according to card
            return getGame().getCurrentPlayer().getLastCard().returnState(getGame());
        } else {
            getGame().setErrorFlag(Boolean.TRUE);
            getGame().setErrorMsg("[ERROR] Invalid card number.\n");
            return this;
        }
    }


    public void saveToFile(Game gameToSave) {
        String filename = "eme.bin";

        // Save game object to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
          fos = new FileOutputStream(filename);
          out = new ObjectOutputStream(fos);
          out.writeObject(gameToSave);
          out.close();
        } catch (Exception ex) {
            getGame().setErrorFlag(Boolean.TRUE);
            getGame().setErrorMsg("Error saving game. Game was not save.");
            ex.printStackTrace();
        }
    }

    
}
