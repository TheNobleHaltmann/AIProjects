package aiassignment3;

import java.util.ArrayList;

// represents a node of the graph
// has a value and can have any number of edges that point to other nodes
public class Node {
    // the edges coming out of this node
    ArrayList<Edge> edges;
    // the value of this node
    String          value;

    // parameter is the value of the node
    public Node ( final String v ) {
        value = v.toLowerCase();
        edges = new ArrayList<Edge>();
    }

    // adds an edge to the node
    public void addEdge ( final Edge e ) {
        edges.add( e );
    }

    // returns the node's list of edges
    public ArrayList<Edge> getEdges () {
        return edges;
    }

    // returns the node's value
    public String getValue () {
        return value;
    }
}
