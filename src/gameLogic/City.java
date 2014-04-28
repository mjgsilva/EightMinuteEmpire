package gameLogic;

public class City implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        // If equals sum 1
        if (o.equals(this))
            return 1;
        return 0;
    }

    public int getIdOfOwner() {
        return idOfOwner;
    }
}