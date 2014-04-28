package gameLogic;

public class Army implements Comparable {
    int idOfOwner;
    String color;
    
    public Army(int id, String color) {
        this.idOfOwner = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "{" + color + idOfOwner + "\u001B[30m" +  "} ";
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