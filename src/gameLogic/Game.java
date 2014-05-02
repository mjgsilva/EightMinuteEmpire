package gameLogic;

import gameLogic.map.*;
import gameLogic.states.*;
import java.util.ArrayList;

public class Game {
    private StateInterface state;
    private StateInterface previousState;

    // Specify if state returned with error. Alternative would've been exceptions
    private Boolean errorFlag = false;
    // Specify error message
    private String errorMsg = "";
    
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    
    private GameMap map = new GameMap(); // DONT FORGET TO CHANGE AFTER READ GAME FROM FILE
    private Deck deck = new Deck(); // DONT FORGET TO CHANGE AFTER READ GAME FROM FILE
    private ArrayList <Card> tableCards = new ArrayList<>();  // Cards turned up in the table - maximum of 6 cards

    // Contructor
    public Game() {
        state = new PrepareGame(this);
        previousState = new PrepareGame(this);
    }

    // Map
    public void setMap(GameMap map) {
        this.map = map;
    }
    
    public GameMap getMap() {
        return map;
    }

    public String getMapAsString() {
        return map.toString();
    }
    
    public StateInterface getState() {
        return state;
    }
    
    
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getPlayerIdAsString(int id) {
        return players.get(id).getIdAsString();
    }
    
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getTableCards() {
        return tableCards;
    }
    
    public Card getCardFromDeck() {
        return deck.getRandomCardFromDeck();
    }
    
    public void addTableCard(Card cardFromDeck) {
        tableCards.add(cardFromDeck);
    }

    public void setTableCards(ArrayList<Card> tableCards) {
        this.tableCards = tableCards;
    }
    
    public void defineGame(int n) {
        state = state.defineGame(n);
    }

    public void defineWinner(ArrayList<Integer> bets) {
        state = state.defineWinner(bets);
    }
    
    public void defineCard(int n) {
        state = state.defineCard(n);
    }

    public void defineAction(int n)
    {
        state = state.defineAction(n);
    }
    
    public void defineEndGame() {
        state = state.defineEndGame();
    }

    public void definePlaceArmy(int regionId) {
        state = state.definePlaceArmy(regionId);
    }
    
    public void defineMoveByLand(int from, int to) {
        state = state.defineMoveByLand(from,to);
    }

    /**
     * Returns table cards in an organized way
     * 
     * @return
     */
    public String getTableCardsAsString() {
        String str = "Card Order (1 2 3 4 5 6)\n";
        for (int i = 0; i < tableCards.size(); i++) {
            if (i == 0)
                str += i+1 + " [0$] " + tableCards.get(i).toString() + "\n";
            else if (i == 1 || i == 2)
                str += i+1 + " [1$] " + tableCards.get(i).toString() + "\n";
            else if (i == 3 || i == 4)
                str += i+1 + " [2$] " + tableCards.get(i).toString() + "\n";
            else if (i == 5)
                str += i+1 + " [3$] " + tableCards.get(i).toString() + "\n";
        }
        return str;
    }
    
    public StateInterface getPreviousState() {
        return previousState;
    }

    public void setPreviousState(StateInterface previousState) {
        this.previousState = previousState;
    }
    
    public Boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(Boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public void defineJokers() {
        state = state.defineJokers();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}