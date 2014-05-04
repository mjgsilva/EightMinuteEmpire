package gameLogic;

import java.io.Serializable;

public class Army implements Serializable {
    int idOfOwner;
    String color;
    
    public Army(int id, String color) {
        this.idOfOwner = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "{" + color + idOfOwner + "\u001B[30m" +  "}";
    }

    public int getIdOfOwner() {
        return idOfOwner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idOfOwner;
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
        final Army other = (Army) obj;
        if (this.idOfOwner != other.idOfOwner) {
            return false;
        }
        return true;
    }
    
}