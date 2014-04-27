package gameLogic.map;

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

    public int getId() {
        return id;
    }
    
    public String toString()
    {
        return "Cont " + id + "\n" + getRegionsExtended();
    }
    
    public String getRegionsExtended()
    {
        String textRegions = new String();
        
        for(Region temp : regions)
            textRegions += temp.toString();
        
        return textRegions;
    }

}
