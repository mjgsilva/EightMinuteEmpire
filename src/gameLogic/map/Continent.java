package gameLogic.map;

import java.util.ArrayList;

public class Continent {
    int id;
    private ArrayList<Region> regions = new ArrayList<>();

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void addRegion(Region r) {
        this.regions.add(r);
    }

    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void addAdjacentToRegion(Region r, Integer id, Integer byLandOrSea) {
        regions.get(regions.indexOf(r)).addAdjacent(id, byLandOrSea);
    }
}
