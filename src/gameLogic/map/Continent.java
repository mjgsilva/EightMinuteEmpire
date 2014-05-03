package gameLogic.map;

import gameLogic.Army;
import java.util.ArrayList;

public class Continent {
    private final int id;
    private final ArrayList<Region> regions;

    public Continent(int id, ArrayList<Region>regions)
    {
        this.id = id;
        this.regions = regions;
    }
    
    public ArrayList<Region> getRegions() {
        return regions;
    }

    public Region getRegion(int id)
    {
        for (Region region : regions) {
            if (region.getId() == id) {
                return region;
            }
        }
        return null;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString()
    {
        return "Continent " + id + "\n" + getRegionsExtended();
    }
    
    public String getRegionsExtended()
    {
        String textRegions = new String();
        
        for(Region temp : regions)
            textRegions += temp.toString() + " ";
        
        return textRegions;
    }
    
    public Boolean placeArmy(int toRegion, Army army) {
        for (Region aux : regions) {
            if (aux.getId() == toRegion) {
                aux.addArmy(army);
                return true;
            }
        }
        return false;
    }
}