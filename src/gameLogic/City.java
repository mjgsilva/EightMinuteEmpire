package gameLogic;

public class City {
    int idOfOwner;
    String color;
    
    public City(int id, String color) {
        this.idOfOwner = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "{" + color + "C" + idOfOwner + "\u001B[30m" +  "} ";
    }
    
    public int getIdOfOwner() {
        return idOfOwner;
    }
}