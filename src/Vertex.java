import java.util.HashSet;

public class Vertex {
    private final String word;
    private final HashSet<Vertex> adjacent;

    public Vertex(String word) {
        this.word = word;
        this.adjacent = new HashSet<>();
    }

    public String word() {
        return word;
    }

    public HashSet<Vertex> adjacent() {
        return adjacent;
    }

    public void setAdjacent(HashSet<Vertex> vs) {
        adjacent.addAll(vs);
    }
}
