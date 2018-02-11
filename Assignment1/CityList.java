package aiassignment2;

import java.util.ArrayList;
import java.util.Collections;

// maintains a list of all the cities found in the file
public class CityList {
    // for use only with movegenerator
    private final ArrayList<City> cities;

    // constructor
    public CityList () {
        cities = new ArrayList<City>();
    }

    // adds a road into the list, adding it to both cities
    public void add ( final String city1, final String city2, final int cost ) {
        boolean city1Found = false;
        boolean city2Found = false;

        for ( int i = 0; i < cities.size(); i++ ) {
            // see if the city is already in the list
            if ( city1.equals( cities.get( i ).name ) ) {
                // if the city is already in the list, update it
                cities.get( i ).adjacent.add( new Road( city2, cost ) );
                city1Found = true;
            }
            else if ( city2.equals( cities.get( i ).name ) ) {
                // if the city is already in the list, update it
                cities.get( i ).adjacent.add( new Road( city1, cost ) );
                city2Found = true;
            }
        }

        // otherwise, make a new city and jam it into the list
        if ( !city1Found ) {
            final City temp1 = new City( city1 );
            temp1.addAdjacent( new Road( city2, cost ) );
            cities.add( temp1 );
        }
        if ( !city2Found ) {
            final City temp2 = new City( city2 );
            temp2.addAdjacent( new Road( city1, cost ) );
            cities.add( temp2 );
        }
    }

    // sorts the list to make searching it easier
    public void sort () {
        Collections.sort( cities );
    }

    // prints out the entire list, for testing
    public void printList () {
        for ( int i = 0; i < cities.size(); i++ ) {
            System.out.println( cities.get( i ).name );
        }
    }

    // gets a city by name
    public City getCityByName ( final String name ) {
        // for now it'll just go through the array
        // i can implement something fancier later
        for ( int i = 0; i < cities.size(); i++ ) {
            if ( name.equals( cities.get( i ).name ) ) {
                // found it
                return cities.get( i );
            }
        }
        // didn't find it
        return null;
    }
}
