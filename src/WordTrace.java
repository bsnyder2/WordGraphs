import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class WordTrace {
    private WordGraph graph;
    private Queue<Vertex> queue;
    private HashSet<Vertex> visited;

    public WordTrace(WordGraph graph) {
        this.graph = graph;
        queue = new LinkedList<>();
        visited = new HashSet<>();
    }
}
