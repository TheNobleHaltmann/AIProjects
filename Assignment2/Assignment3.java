package aiassignment3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Assignment3 {

    // keeps track of all nodes in graph
    private final Hashtable<String, Node> nodes;

    // initialize the graph
    private Assignment3 () {

        nodes = new Hashtable<String, Node>( 100 );
        // add all nodes to the hashtable
        nodes.put( "animals", new Node( "animals" ) );
        nodes.put( "flying", new Node( "flying" ) );
        nodes.put( "swimming", new Node( "swimming" ) );
        nodes.put( "walking", new Node( "walking" ) );
        nodes.put( "birds", new Node( "birds" ) );
        nodes.put( "yellow", new Node( "yellow" ) );
        nodes.put( "fishes", new Node( "fishes" ) );
        nodes.put( "penguins", new Node( "penguins" ) );
        nodes.put( "canaries", new Node( "canaries" ) );
        nodes.put( "nemo", new Node( "nemo" ) );
        nodes.put( "opus", new Node( "opus" ) );
        nodes.put( "tweety", new Node( "tweety" ) );
        nodes.put( "robin", new Node( "robin" ) );
        nodes.put( "white", new Node( "white" ) );

        // add the edges to the nodes
        nodes.get( "birds" ).addEdge( new Edge( nodes.get( "birds" ), nodes.get( "flying" ), "travel" ) );
        nodes.get( "birds" ).addEdge( new Edge( nodes.get( "birds" ), nodes.get( "animals" ), "ako" ) );
        nodes.get( "fishes" ).addEdge( new Edge( nodes.get( "fishes" ), nodes.get( "swimming" ), "travel" ) );
        nodes.get( "fishes" ).addEdge( new Edge( nodes.get( "fishes" ), nodes.get( "animals" ), "ako" ) );
        nodes.get( "nemo" ).addEdge( new Edge( nodes.get( "nemo" ), nodes.get( "fishes" ), "isa" ) );
        nodes.get( "penguins" ).addEdge( new Edge( nodes.get( "penguins" ), nodes.get( "birds" ), "ako" ) );
        nodes.get( "penguins" ).addEdge( new Edge( nodes.get( "penguins" ), nodes.get( "walking" ), "travel" ) );
        nodes.get( "opus" ).addEdge( new Edge( nodes.get( "opus" ), nodes.get( "penguins" ), "isa" ) );
        nodes.get( "canaries" ).addEdge( new Edge( nodes.get( "canaries" ), nodes.get( "birds" ), "ako" ) );
        nodes.get( "canaries" ).addEdge( new Edge( nodes.get( "canaries" ), nodes.get( "yellow" ), "color" ) );
        nodes.get( "robin" ).addEdge( new Edge( nodes.get( "robin" ), nodes.get( "canaries" ), "isa" ) );
        nodes.get( "tweety" ).addEdge( new Edge( nodes.get( "tweety" ), nodes.get( "canaries" ), "isa" ) );
        nodes.get( "tweety" ).addEdge( new Edge( nodes.get( "tweety" ), nodes.get( "white" ), "color" ) );

    }

    // ask user for command line input, output answer
    private void run () {
        final Scanner input = new Scanner( System.in );
        System.out.println( "Input format: sourceNode relationship" );

        while ( input.hasNext() ) {

            // the user will input the source node and the desired relationship
            final String nodeValue = input.next().toLowerCase();

            // exit the program if user typed quit
            if ( nodeValue.toLowerCase().equals( "quit" ) ) {
                input.close();
                System.out.println( "Program terminated." );
                return;
            }

            // user didn't type quit so let's see what they did type
            final String relationship = input.next().toLowerCase();

            final Node sourceNode = nodes.get( nodeValue );
            if ( sourceNode == null ) {
                System.out.println( "Given source node not found in graph." );
            }
            else {
                if ( relationship.equals( "isa" ) ) {
                    // looking for all isa relationships
                    final ArrayList<Edge> edges = sourceNode.getEdges();
                    Edge e = null;

                    // find the isa edge if one exists for this node
                    for ( int i = 0; i < edges.size(); i++ ) {
                        if ( edges.get( i ).isa() ) {
                            e = edges.get( i );
                            i = edges.size();
                        }
                    }

                    // if we did not find an isa edge, say there were no results
                    if ( e == null ) {
                        System.out.println( "No results found." );
                    }
                    else {
                        // if we did find an isa, report it, and all akos above
                        System.out.println( capitalizeFirst( sourceNode.getValue() ) + " is a "
                                + depluralize( e.getEnd().getValue() ) + "." );
                        Node n = e.getEnd();

                        // while n has edges, check for akos
                        while ( !n.getEdges().isEmpty() ) {
                            for ( int i = 0; i < n.getEdges().size(); i++ ) {
                                if ( n.getEdges().get( i ).ako() ) {
                                    e = n.getEdges().get( i );
                                    System.out.println( capitalizeFirst( sourceNode.getValue() ) + " is a "
                                            + depluralize( e.getEnd().getValue() ) + "." );
                                    i = n.getEdges().size();
                                    n = e.getEnd();
                                }
                            }
                        }

                    }

                }
                else if ( relationship.equals( "ako" ) ) {
                    // looking for all ako relationships
                    final ArrayList<Edge> edges = sourceNode.getEdges();
                    Edge e = null;

                    // find the ako edge if one exists for this node
                    for ( int i = 0; i < edges.size(); i++ ) {
                        if ( edges.get( i ).ako() ) {
                            e = edges.get( i );
                            i = edges.size();
                        }
                    }

                    // if we did not find an ako edge, say there were no results
                    if ( e == null ) {
                        System.out.println( "No results found." );
                    }
                    else {
                        // if we did find an ako, report it, and all akos above
                        System.out.println( capitalizeFirst( sourceNode.getValue() ) + " are a kind of "
                                + e.getEnd().getValue() + "." );
                        Node n = e.getEnd();

                        // while n has edges, check for akos
                        while ( !n.getEdges().isEmpty() ) {
                            for ( int i = 0; i < n.getEdges().size(); i++ ) {
                                if ( n.getEdges().get( i ).ako() ) {
                                    e = n.getEdges().get( i );
                                    System.out.println( capitalizeFirst( sourceNode.getValue() ) + " are a kind of "
                                            + e.getEnd().getValue() + "." );
                                    i = n.getEdges().size();
                                    n = e.getEnd();
                                }
                            }
                        }

                    }
                }
                else {
                    // looking for specific edge label
                    boolean notDone = true;
                    // first, check if this node has that edge label
                    for ( int i = 0; i < sourceNode.getEdges().size(); i++ ) {
                        if ( sourceNode.getEdges().get( i ).getLabel().equals( relationship ) ) {
                            // we found it already
                            // I can't think of how to make this format nicely
                            System.out.println(
                                    capitalizeFirst( sourceNode.getEdges().get( i ).getEnd().getValue() ) + "." );
                            i = sourceNode.getEdges().size();
                            notDone = false;
                        }
                    }

                    // if we haven't found it yet, find an ako edge or an isa
                    // edge to travel along and try there
                    if ( notDone ) {
                        Node a = null;
                        for ( int i = 0; i < sourceNode.getEdges().size(); i++ ) {
                            if ( sourceNode.getEdges().get( i ).ako() || sourceNode.getEdges().get( i ).isa() ) {
                                // found the edge
                                a = sourceNode.getEdges().get( i ).getEnd();
                                i = sourceNode.getEdges().size();
                            }
                        }

                        if ( a == null ) {
                            // didn't find any ako or isa edges
                            System.out.println( "No results found." );
                        }
                        else {

                            // now start the loop
                            while ( a != null ) {
                                // try to find the right label in a's edges
                                for ( int i = 0; i < a.getEdges().size(); i++ ) {
                                    if ( a.getEdges().get( i ).getLabel().equals( relationship ) ) {
                                        System.out.println(
                                                capitalizeFirst( a.getEdges().get( i ).getEnd().getValue() ) + "." );
                                        a = null;
                                        break;
                                    }
                                }

                                // if didn't find the right label there, keep
                                // going up the chain
                                if ( a != null ) {
                                    notDone = true;
                                    for ( int i = 0; i < a.getEdges().size(); i++ ) {
                                        if ( a.getEdges().get( i ).ako() ) {
                                            // found the ako edge
                                            a = a.getEdges().get( i ).getEnd();
                                            i = a.getEdges().size();
                                            notDone = false;
                                        }
                                    }
                                    if ( notDone ) {
                                        // didn't find any more ako edges
                                        System.out.println( "No results found." );
                                        a = null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // since I made everything plural, this will help print the results
    // this won't work for every word, but will for those in this graph
    public String depluralize ( final String s ) {
        if ( s.substring( s.length() - 3 ).equals( "ies" ) ) {
            return s.substring( 0, s.length() - 3 ) + "y";
        }
        else if ( s.substring( s.length() - 2 ).equals( "es" ) ) {
            return s.substring( 0, s.length() - 2 );
        }
        else if ( s.substring( s.length() - 1 ).equals( "s" ) ) {
            return s.substring( 0, s.length() - 1 );
        }

        return null;
    }

    // capitalizes the first letter, to improve output
    public String capitalizeFirst ( final String s ) {
        return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
    }

    // when the program is ran, create an Assignment3 object and let it do its
    // thing
    public static void main ( final String[] args ) {
        final Assignment3 a = new Assignment3();
        a.run();
    }
}
