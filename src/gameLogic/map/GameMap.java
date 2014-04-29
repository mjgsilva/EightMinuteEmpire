package gameLogic.map;

import gameLogic.Army;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class GameMap {
    private final ArrayList<Continent> continents = new ArrayList<>();
    
    public GameMap()
    {
        readMapXML();
    }
    
    public void placeArmy(int toRegion, Army army) {
        for (Continent aux : continents) {
            if (aux.placeArmy(toRegion, army))
                break;
        }
    }
    
    private void addContinent(int id, ArrayList<Region> regions)
    {
        Continent c = new Continent(id, regions);
        continents.add(c);
    }
    
    /* Search a region by id using a loop over the continents array list*/
    public Region getRegionById(int id)
    {
        Region r;
        for(Continent temp : continents)
            if((r=temp.getRegion(id)) != null)
                return r;
        return null;
    }
    
    @Override
    public String toString()
    {
        return getContinentsExtended();
    }
    
    private String getContinentsExtended()
    {
        String textContinents = new String();
        
        for(Continent temp : continents)
            textContinents += temp.toString();
        
        return textContinents;
    }
    
    private void readMapXML()
    {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("src/gameLogic/map/GameMap.xml"));

            doc.getDocumentElement().normalize();

            NodeList listOfContinents = doc.getElementsByTagName("Continent");
            int totalContinents = listOfContinents.getLength();

            for (int i = 0; i < totalContinents; i++) {
                int continentId;
                int totalRegions;
                ArrayList<Region> regions = new ArrayList<>();

                Node continentNode = listOfContinents.item(i);
                Element continentElement = (Element) continentNode;

                continentId = Integer.parseInt(continentElement.getAttribute("id"));

                NodeList listOfRegionsByContinent = continentElement.getElementsByTagName("Region");
                totalRegions = listOfRegionsByContinent.getLength();

                for (int j = 0; j < totalRegions; j++) {
                    int regionId;
                    int totalAdjacents;
                    Map<Integer, Integer> adjacent = new HashMap<>();

                    Node regionNode = listOfRegionsByContinent.item(j);
                    Element regionElement = (Element) regionNode;

                    regionId = Integer.parseInt(regionElement.getAttribute("id"));

                    NodeList listOfAdjacentsByRegion = regionElement.getElementsByTagName("adjacent");
                    totalAdjacents = listOfAdjacentsByRegion.getLength();

                    for (int k = 0; k < totalAdjacents; k++) {
                        int adjacentId;
                        int adjacentConn;

                        Node adjacentNode = listOfAdjacentsByRegion.item(k);
                        Element adjacentElement = (Element) adjacentNode;

                        NodeList adjacentRegionList = adjacentElement.getElementsByTagName("reg");
                        Element adjacentRegion = (Element) adjacentRegionList.item(0);

                        NodeList textAdjacentRegion = adjacentRegion.getChildNodes();
                        adjacentId = Integer.parseInt(((Node) textAdjacentRegion.item(0)).getNodeValue().trim());

                        NodeList adjacentConnectionList = adjacentElement.getElementsByTagName("connection");
                        Element adjacentConnection = (Element) adjacentConnectionList.item(0);

                        NodeList textAdjacentConnection = adjacentConnection.getChildNodes();
                        adjacentConn = Integer.parseInt(((Node) textAdjacentConnection.item(0)).getNodeValue().trim());

                        adjacent.put(adjacentId, adjacentConn);
                    }
                    regions.add(new Region(regionId,adjacent));
                }
                addContinent(continentId, regions);
             }
        } catch (SAXParseException err) {
            System.out.println(" " + err.getMessage ());
        } catch (SAXException e) {
            Exception x = e.getException ();
                ((x == null) ? e : x).printStackTrace ();
        } catch (Throwable t) {
            t.printStackTrace ();
        }
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }
    
}