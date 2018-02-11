package aiassignment2;

// represents a road between two cities
public class Road {
    // for use only with movegenerator

    // where the road goes to
    public String destination;
    // how long the road is
    public int    cost;

    // constructor
    public Road ( final String d, final int c ) {
        destination = d;
        cost = c;
    }
}
