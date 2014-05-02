package gameLogic.map;

import gameLogic.Army;
import gameLogic.City;
import gameLogic.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Region {
    private final int id;
    private final Map <Integer, Integer> adjacent;
    private ArrayList<Army> armies = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    
    // Contructor
    public Region(int id, Map<Integer, Integer>adjacent) {
        this.id = id;
        this.adjacent = adjacent;
    }
    
    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getAdjacent() {
        return adjacent;
    }
    
    @Override
    public String toString()
    {
        return "\tRegion " + id + " " + armies.toString() + "\n";
    }
    
    public boolean checkAdjacencyByLand(int id)
    {
        for (Map.Entry pairs : adjacent.entrySet()) {
            if(Integer.parseInt(pairs.getKey().toString()) == id)
                return true;
        }
        return false;
    }
    
    public boolean checkArmiesOfPlayerOnRegion(Player tempPlayer)
    {
        int playerId;
        String playerColor;
        playerId = tempPlayer.getId();
        playerColor = tempPlayer.getColor();
        
        Army tempArmy = new Army(playerId, playerColor);
        for(Army armyLoop : armies)
        {
            if(armyLoop.equals(tempArmy))
                return true;
        }
        return false;
    }
    
    public boolean checkCitiesOfPlayerOnRegion(Player tempPlayer)
    {
        int playerId;
        String playerColor;
        playerId = tempPlayer.getId();
        playerColor = tempPlayer.getColor();
        
        City tempCity = new City(playerId, playerColor);
        
        for (City cityLoop : cities)
        {
            if(tempCity.equals(cityLoop))
                return true;
        }
        return false;
    }
    
    public String getAdjacentExtended()
    {
        String adjacentText = new String();
        for (Map.Entry pairs : adjacent.entrySet()) {
            adjacentText += ("\t\t" + pairs.getKey() + " / " + pairs.getValue() + "\n");
        }
        return adjacentText;
    }
    
    public ArrayList<Army> getArmies() {
        return armies;
    }

    public void setArmies(ArrayList<Army> armies) {
        this.armies = armies;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }
    
    public void removeArmy(Army army) {
        armies.remove(army);
    }
    
    /**
     * Returns owner of this Region, if draw or no army then returns -1
     * 
     * @param nPlayers
     * @return ownerId
     */
    public int returnOwner(int nPlayers) {
        // 0 - No one controls this region
        // n - Player n controls this region
        if (armies.isEmpty() && cities.isEmpty())        
            return 0-1;
        else {
            ArrayList <Integer> players = new ArrayList<>();
            for (int i = 1; i <= nPlayers; i++) {
                players.add(0);
                for (Army auxArmy : armies) {
                    if (auxArmy.getIdOfOwner() == i)
                        players.set(i-1, players.get(i-1) + 1);
                }
                for (City auxCity : cities) {
                    if (auxCity.getIdOfOwner() == i)
                        players.set(i-1, players.get(i-1) + 1);
                }
            }
            int flag = 0;
            for (Integer aux : players)
                if (aux.equals(Collections.max(players)))
                    flag++;
            // minor or equal 0 isn't necessary but you never can't be too carefull
            if (flag >= 2 || Collections.max(players) <= 0)
                return -1;
            else
                return players.indexOf(Collections.max(players)) + 1;
        }
    }
}
