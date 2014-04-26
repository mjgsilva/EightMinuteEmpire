package gameLogic.map;

import java.util.HashMap;
import java.util.Map;
public class Region {
    private int id;
    private Map <Integer, Integer> adjacent = new HashMap<>();

    public Region(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getAdjacent() {
        return adjacent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAdjacent(Integer regionId, Integer byLandOrSea) {
        this.adjacent.put(regionId, byLandOrSea);
    }
}
