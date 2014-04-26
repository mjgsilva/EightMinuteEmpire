package gameLogic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck()
    {
        readXML();
    }

    public void addCard(int id, int typeOfResource, int numberOfResource, int fivePlayersCard, int[][] actions)
    {
        Card c = new Card(id, typeOfResource, numberOfResource, fivePlayersCard, actions);

        cards.add(c);
    }

    public void removeCard(Card tempCard)
    {
        cards.remove(cards.indexOf(tempCard));
    }

    public void list()
    {
        for(Card temp : cards)
            System.out.println(temp.toString());
    }

    
    private void readXML() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("src/gameLogic/DeckOfCards.xml"));

            doc.getDocumentElement().normalize();

            NodeList listOfCards = doc.getElementsByTagName("Card");
            int totalCards = listOfCards.getLength();

            for(int i=0; i < totalCards; i++)
            {
                int typeOfResource;
                int numberOfResource;
                int fivePlayersCard;
                int howManyActions;
                int[][] actions;


                Node cardNode = listOfCards.item(i);
                Element cardElement = (Element)cardNode;

                    /* Type of Resource */
                NodeList cardTypeOfResouceList = cardElement.getElementsByTagName("typeOfResource");
                Element cardTypeOfResouce = (Element)cardTypeOfResouceList.item(0);

                NodeList textCardTypeOfResource = cardTypeOfResouce.getChildNodes();
                typeOfResource = Integer.parseInt(((Node)textCardTypeOfResource.item(0)).getNodeValue().trim());

                    /* Number of Resources */
                NodeList cardNumberOfResourceList = cardElement.getElementsByTagName("numberOfResources");
                Element cardNumberOfResource = (Element)cardNumberOfResourceList.item(0);

                NodeList textNumberOfResource = cardNumberOfResource.getChildNodes();
                numberOfResource = Integer.parseInt(((Node)textNumberOfResource.item(0)).getNodeValue().trim());

                    /* Five Players Card */
                NodeList cardFivePlayersCardList = cardElement.getElementsByTagName("fivePlayersCard");
                Element cardFivePlayersCard = (Element)cardFivePlayersCardList.item(0);

                NodeList textFivePlayersCard = cardFivePlayersCard.getChildNodes();
                fivePlayersCard = Integer.parseInt(((Node)textFivePlayersCard.item(0)).getNodeValue().trim());

                    /* How Many Action this Card supports */

                NodeList listOfActions = cardElement.getElementsByTagName("action");
                howManyActions = listOfActions.getLength();

                actions = new int[howManyActions][2];

                    /* Fill actions array */

                for(int j=0; j < howManyActions; j++)
                {

                    int typeActionCard;
                    int howManyCard;

                    Node actionNode = listOfActions.item(j);
                    Element actionElement = (Element)actionNode;

                    NodeList cardTypeActionList = actionElement.getElementsByTagName("type");
                    Element cardTypeAction = (Element)cardTypeActionList.item(0);

                    NodeList textActionCard = cardTypeAction.getChildNodes();
                    typeActionCard = Integer.parseInt(((Node)textActionCard.item(0)).getNodeValue().trim());

                    NodeList cardHowManyList = actionElement.getElementsByTagName("howMany");
                    Element cardHowMany = (Element)cardHowManyList.item(0);

                    NodeList textHowMany = cardHowMany.getChildNodes();
                    howManyCard = Integer.parseInt(((Node)textHowMany.item(0)).getNodeValue().trim());

                    actions[j][0] = typeActionCard;
                    actions[j][1] = howManyCard;
                }
                addCard((i+1),typeOfResource,numberOfResource,fivePlayersCard,actions);
            }


        } catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());

        } catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

        } catch (Throwable t) {
            t.printStackTrace ();
        }
    }
}
