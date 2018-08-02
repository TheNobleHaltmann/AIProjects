package aiassignment3;

// represents an edge in the graph
// has a start node, an end node, and a label
public class Edge {
    // the start node - the one the arrow points away from
    private final Node   start;
    // the label on the edge
    private final String type;
    // the end node - the one the arrow points to
    private final Node   end;

    // constructor takes start node, end node, and type of edge
    public Edge ( final Node s, final Node e, final String t ) {
        start = s;
        type = t.toLowerCase();
        end = e;
    }

    // returns the start node - the one the arrow points away from
    public Node getStart () {
        return start;
    }

    // returns the end node - the one the arrow points to
    public Node getEnd () {
        return end;
    }

    // returns the edge label
    public String getLabel () {
        return type;
    }

    // true if this is an ako edge
    public boolean ako () {
        return type.equals( "ako" );
    }

    // true if this is an isa edge
    public boolean isa () {
        return type.equals( "isa" );
    }
}
