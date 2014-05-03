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
        return "{" + color + "C" + idOfOwner + "\u001B[30m" +  "}";
    }
    
    public int getIdOfOwner() {
        return idOfOwner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idOfOwner;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final City other = (City) obj;
        if (this.idOfOwner != other.idOfOwner) {
            return false;
        }
        return true;
    }
    
    
}