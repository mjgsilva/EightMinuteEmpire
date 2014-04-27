package gameLogic.map;

import java.util.Iterator;
import java.util.Map;

public class Region {
    private final int id;
    private final Map <Integer, Integer> adjacent;

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
    
    public String toString()
    {
        return "\tReg: " + id + "\n" + getAdjacentExtended();
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
        it.remove();
        return adjacentText;
    }
}
