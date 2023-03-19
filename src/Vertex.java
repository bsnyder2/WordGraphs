import java.util.HashSet;

public class Vertex {
    private String word;
    private HashSet<Vertex> adjacent;

    public Vertex(String word) {
        this.word = word;
        this.adjacent = new HashSet<>();
    }
    public void addAllAdjacent(HashSet<Vertex> vs) {
        this.adjacent.addAll(vs);
    }
}
