package aiassignment2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

// runs the specified search method to find the best path from one city to
// another
public class SearchUSA {

    // path to the roads input file
    private final String                roadsPath      = "usroads.pl";
    // path to the lat/long input file
    private final String                citiesPath     = "uscities.pl";
    // the movegenerator, which knows the entire graph
    private MoveGenerator               m;
    // which mode - A*, greedy, or dp
    private Mode                        mode;
    // input string for A* mode
    private final String                ASTAR_STRING   = "astar";
    // input string for greedy best-first mode
    private final String                GREEDY_STRING  = "greedy";
    // input string for dynamic programming mode
    private final String                DYNAMIC_STRING = "dp";
    // the frontier has a priority queue and a hash table representation
    private PriorityQueue<CityNode>     frontierQueue;
    private Hashtable<String, CityNode> frontierTable;
    // the explored set has only a hash table representation
    private Hashtable<String, CityNode> explored;
    // the cities we explored along the way
    private String                      exploredString = "";

    // what search mode - A*, Greedy Best-First, or Dynamic Programming
    public enum Mode {
        ASTAR, GREEDY, DYNAMIC
    }

    // does everything
    private SearchUSA ( final String[] args ) {

        // check right amount of args
        if ( args.length != 3 ) {
            // wrong use
            System.out.println( "Usage: java -jar SearchUSA.jar searchtype srccityname destcityname" );
            return;
        }

        // check proper search type
        if ( args[0].equals( ASTAR_STRING ) ) {
            mode = Mode.ASTAR;
        }
        else if ( args[0].equals( GREEDY_STRING ) ) {
            mode = Mode.GREEDY;
        }
        else if ( args[0].equals( DYNAMIC_STRING ) ) {
            mode = Mode.DYNAMIC;
        }
        else {
            // wrong search type
            System.out
                    .println( "Search type must be " + ASTAR_STRING + ", " + GREEDY_STRING + ", or " + DYNAMIC_STRING );
            return;
        }

        // move generator reads in file
        m = new MoveGenerator( roadsPath, citiesPath );

        // check that start city exists in file
        if ( !m.isCityInList( args[1] ) ) {
            // starting city not in list
            System.out.println( "Starting city must be on map" );
            return;
        }

        // check that destination city exists in file
        if ( !m.isCityInList( args[2] ) ) {
            // destination city not in list
            System.out.println( "Destination city must be on map" );
            return;
        }

        // all inputs valid, map has been read in, we're good to go
        frontierQueue = new PriorityQueue<CityNode>();
        frontierTable = new Hashtable<String, CityNode>( 100 );
        explored = new Hashtable<String, CityNode>( 100 );

        // firstly, make the initial node
        final CityNode initial = new CityNode( args[1], 0, null );
        m.setLatAndLong( initial );
        addToFrontier( initial );

        // this is used for the straight line heuristic thing
        final CityNode destination = new CityNode( args[2], 0, null );
        m.setLatAndLong( destination );

        // loop while frontier is not empty
        while ( !frontierQueue.isEmpty() ) {
            // get the next node to expand
            final CityNode current = removeFromFrontier();

            // System.out.println( "picked: " + current.cityName + " " +
            // current.pathCost );

            // did we reach the end
            if ( current.cityName.equals( args[2] ) ) {
                writeOutput( current );
                return;
            }

            // not at goal yet
            // add node to explored
            addToExplored( current );
            // generate moves for node
            final ArrayList<CityNode> adjacent = m.generateMove( current );

            // gotta deal with all the moves generated
            for ( int i = 0; i < adjacent.size(); i++ ) {
                // update each resulting node's cost based on current mode
                if ( mode == Mode.DYNAMIC ) {
                    // update path cost with cost of parent
                    adjacent.get( i ).pathCost += current.pathCost;
                }
                else if ( mode == Mode.GREEDY ) {
                    // update path cost with expected path cost
                    adjacent.get( i ).pathCost = estimatePathCost( adjacent.get( i ), destination );
                }
                else if ( mode == Mode.ASTAR ) {

                    // update distanceToRoot
                    if ( adjacent.get( i ).parentCity != null ) {
                        adjacent.get( i ).distanceToRoot = adjacent.get( i ).distanceToParent
                                + adjacent.get( i ).parentCity.distanceToRoot;
                    }

                    // path cost is sum of distance to root and estimate
                    adjacent.get( i ).pathCost = estimatePathCost( adjacent.get( i ), destination )
                            + adjacent.get( i ).distanceToRoot;

                }

                // then stick node into the frontier
                addToFrontier( adjacent.get( i ) );
            }
        }

        System.out.println( "Path not found." );

    }

    // adds a citynode onto the frontier
    public void addToFrontier ( final CityNode c ) {
        // check against explored set
        if ( explored.contains( c ) ) {
            // already in explored set
            return;
        }

        // check against frontier table
        if ( frontierTable.contains( c ) ) {
            // already in frontier set - might need to update values
            if ( frontierTable.get( c.cityName ).pathCost <= c.pathCost ) {
                // the one already in the table is shorter than the new node so
                // we don't care
                return;
            }
            else {
                // the new node is shorter so get rid of the old one
                frontierTable.remove( c.cityName, c );
                frontierQueue.remove( c );
                // then stick the new node in instead - down below
            }
        }

        // add to frontier - table and queue
        frontierTable.put( c.cityName, c );
        frontierQueue.add( c );

        // System.out.println( "added: " + c.cityName + " " + c.pathCost );

    }

    // removes the next citynode from the frontier to explore
    public CityNode removeFromFrontier () {
        // get from priority queue
        final CityNode ret = frontierQueue.poll();

        // remove from hash table
        frontierTable.remove( ret.cityName, ret );

        // bing bang boom
        return ret;
    }

    // adds a citynode to the explored set
    public void addToExplored ( final CityNode c ) {
        explored.put( c.cityName, c );
        exploredString += c.cityName + ", ";
    }

    // estimates the remaining path cost using straight-line heuristic
    public int estimatePathCost ( final CityNode cn, final CityNode dest ) {

        double a = 69.5 * ( cn.latitude - dest.latitude );
        a = a * a;

        double b = ( cn.latitude + dest.latitude ) / 360.0;
        b = b * Math.PI;
        b = Math.cos( b );
        b = b * 69.5;

        double c = cn.longitude - dest.longitude;
        c = c * c;

        double d = b * c;
        d = d + a;
        d = Math.sqrt( d );

        return (int) Math.round( d );
    }

    // writes the output when path found
    public void writeOutput ( CityNode cn ) {

        // calculate path distance
        int distance = 0;

        // get array of all visited nodes on path
        final ArrayList<String> path = new ArrayList<String>();
        while ( cn.parentCity != null ) {
            path.add( cn.cityName );
            distance += cn.distanceToParent;
            cn = cn.parentCity;
        }
        // get starting city in there also
        path.add( cn.cityName );

        // write out the city names in backward order
        String out = "Path: ";
        for ( int i = path.size() - 1; i >= 0; i-- ) {
            out += path.get( i ) + ", ";
        }

        // get rid of trailing commas
        exploredString = exploredString.substring( 0, exploredString.length() - 2 );
        out = out.substring( 0, out.length() - 2 );

        System.out.println( "Nodes explored: " + exploredString );
        System.out.println( "Number of nodes expanded: " + explored.size() );
        System.out.println( out );
        System.out.println( "Number of nodes in path: " + path.size() );
        System.out.println( "Total distance: " + distance );
    }

    // just creates a SearchUSA object
    public static void main ( final String[] args ) {
        new SearchUSA( args );
    }

}
