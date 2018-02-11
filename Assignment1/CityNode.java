package aiassignment2;

// represents a single node of the frontier or explored set
public class CityNode implements Comparable<CityNode> {
    // used by SearchUSA

    /// the name of the city, also the state label
    public String   cityName;
    // how far from this node to its parent
    public int      distanceToParent;
    // the priority of expanding this node, is calculated based on mode
    public int      pathCost;
    // the parent node of this node
    public CityNode parentCity;
    // the city's latitude and longitude from the file
    public double   latitude;
    public double   longitude;
    // what action was used to generate this node (always MOVE)
    public String   action;
    // used by A* calculation, the distance from this city to the root
    public int      distanceToRoot;

    // constructor with lat and long
    public CityNode ( final String name, final int cost, final CityNode parent, final double lat, final double lon ) {
        cityName = name;
        pathCost = cost;
        distanceToParent = cost;
        parentCity = parent;
        latitude = lat;
        longitude = lon;

        action = "MOVE";
        distanceToRoot = 0;
    }

    // constructor without lat and long
    public CityNode ( final String name, final int cost, final CityNode parent ) {

        this( name, cost, parent, 0.0, 0.0 );

    }

    // writing node as a string just writes the city name
    @Override
    public String toString () {
        return cityName;
    }

    // object comparisons based on path cost
    @Override
    public int compareTo ( final CityNode other ) {
        return this.pathCost - other.pathCost;
    }

    // equals another node if city names match
    @Override
    public boolean equals ( final Object o ) {
        final CityNode other = (CityNode) o;
        return ( this.cityName.equals( other.cityName ) );
    }

}
