package gameLogic;

public class Army {
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
    
    
}
