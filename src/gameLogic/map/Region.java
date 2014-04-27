package gameLogic.map;

import gameLogic.Army;
import gameLogic.City;
import java.util.ArrayList;
import java.util.Iterator;
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
        if (id == 12)
            return "\tRegion " + id + " Initial Region " + armies.toString() + "\n";
        else
            return "\tRegion " + id + " " + armies.toString() + "\n";
    }
    
    public String getAdjacentExtended()
    {
        String adjacentText = new String();
        Iterator it = adjacent.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
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

    void addArmy(Army army) {
        armies.add(army);
    }
    
    void removeArmy(Army army) {
        armies.remove(army);
    }
}
