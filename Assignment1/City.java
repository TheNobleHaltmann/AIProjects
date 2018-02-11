package aiassignment2;

import java.util.ArrayList;

// a single city in the citylist
public class City implements Comparable<City> {
    // for use with move generator only

    // name of city
    public String          name;
    // what roads connect to the city
    public ArrayList<Road> adjacent;
    // latitude and longitude from file
    public double          latitude;
    public double          longitude;

    // constructor only needs a name
    public City ( final String n ) {
        name = n;
        adjacent = new ArrayList<Road>();
    }

    // adds a road to the city
    public void addAdjacent ( final Road r ) {
        adjacent.add( r );
    }

    // sets the city's latitude and longitude
    public void setLatLong ( final double lat, final double lon ) {
        latitude = lat;
        longitude = lon;
    }

    // same as another city if names are the same
    @Override
    public int compareTo ( final City other ) {
        return this.name.compareTo( other.name );
    }

}
