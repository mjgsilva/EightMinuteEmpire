package gameLogic.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Map extends DefaultHandler {
    private ArrayList<Continent> continents = new ArrayList<>();
    
    
    
    private Continent tempContinent;
    private String tmpValue;
    private int pos = 0;
    
    
    public Map(final String fileName) {
        // Map generator from xml file - Provisory
        parseDocument(fileName);
        
    }

    private void parseDocument(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(fileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element
        if (elementName.equalsIgnoreCase("Continent")) {
            tempContinent = new Continent();
            tempContinent.setId(Integer.parseInt(attributes.getValue("id")));
        }
        // if current element is publisher
        if (elementName.equalsIgnoreCase("Region")) {
            tempContinent.addRegion(new Region(Integer.parseInt(attributes.getValue("id"))));  
        }
        
        if (elementName.equalsIgnoreCase("adjacent")) {
            tempContinent.addAdjacentToRegion(tempContinent.getRegions().get(pos),
                    Integer.parseInt(attributes.getValue("key")),
                    Integer.parseInt(attributes.getValue("value")));
        }
    }
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("Continent")) {
            continents.add(tempContinent);
            pos = 0;
        }
        if (element.equalsIgnoreCase("Region"))
            pos++;
    }
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
    }

    
    public String getContinents() {
        return continents.toString();
    }
}
