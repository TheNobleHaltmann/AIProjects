package aiassignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// responsible for reading in the input files and for determining what cities
// are reachable from a given city
public class MoveGenerator {

    // this list contains all the cities in the file
    // is only used by the move generator, not the search algorithm
    private final CityList cities;

    // constructor given locations of files to read from
    public MoveGenerator ( final String roadsLoc, final String citiesLoc ) {
        cities = new CityList();

        Scanner scan;
        try {
            scan = new Scanner( new File( roadsLoc ) );

            while ( scan.hasNext() ) {
                String city1 = scan.next();
                city1 = city1.substring( 5, city1.length() - 1 );
                // System.out.println( city1 );
                String city2 = scan.next();
                city2 = city2.substring( 0, city2.length() - 1 );
                // System.out.println( city2 );
                String costStr = scan.next();
                costStr = costStr.substring( 0, costStr.length() - 2 );
                final int cost = Integer.parseInt( costStr );
                // System.out.println( cost );
                // System.out.println( "city1: " + city1 + " city2: " + city2 +
                // " cost: " + cost );
                cities.add( city1, city2, cost );
            }

            scan.close();
            cities.sort();

            // throw in lats and longs
            scan = new Scanner( new File( citiesLoc ) );
            while ( scan.hasNext() ) {
                String name = scan.next();
                name = name.substring( 5, name.length() - 1 );
                String latStr = scan.next();
                latStr = latStr.substring( 0, latStr.length() - 1 );
                String lonStr = scan.next();
                lonStr = lonStr.substring( 0, lonStr.length() - 2 );
                final City c = cities.getCityByName( name );
                c.latitude = Double.parseDouble( latStr );
                c.longitude = Double.parseDouble( lonStr );
            }
            scan.close();

        }
        catch ( final FileNotFoundException e ) {
            System.out.println( "somethin broke" );
            System.exit( 0 );
        }

    }

    // generates all possible moves from a given citynode
    // returns moves as list of nodes
    public ArrayList<CityNode> generateMove ( final CityNode cn ) {
        // find the city corresponding to the city node
        final City c = cities.getCityByName( cn.cityName );

        // for each adjacency of that city, create a cityNode
        final ArrayList<CityNode> returnList = new ArrayList<CityNode>();
        for ( int i = 0; i < c.adjacent.size(); i++ ) {
            final Road r = c.adjacent.get( i );

            // this will just return the cost on the graph; the other side can
            // figure out what to do with it based on the mode
            returnList.add( new CityNode( r.destination, r.cost, cn ) );
            setLatAndLong( returnList.get( returnList.size() - 1 ) );
        }

        // return the list
        return returnList;
    }

    // sets the lat and long of a citynode with values from file
    public void setLatAndLong ( final CityNode cn ) {
        final City c = cities.getCityByName( cn.cityName );
        cn.latitude = c.latitude;
        cn.longitude = c.longitude;
    }

    // returns true if city appears in list
    // used to check input values from user
    public boolean isCityInList ( final String name ) {
        final City c = cities.getCityByName( name );
        return ( c != null );
    }
}
