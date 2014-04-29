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
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Army)) return false;

        final Army temp = (Army)obj;
        return this.idOfOwner == temp.idOfOwner;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.idOfOwner;
        return hash;
    }

    public int getIdOfOwner() {
        return idOfOwner;
    }
}