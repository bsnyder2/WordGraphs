import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class WordTrace {
    private final WordGraph graph;
    private final String startWord;
    private final String endWord;
    private final HashMap<Vertex, Vertex> parents;

    public WordTrace(WordGraph graph, String startWord, String endWord) {
        this.graph = graph;
        this.startWord = startWord;
        this.endWord = endWord;
        this.parents = new HashMap<>();
    }

    public int BFS() {
        if (!graph.validWords().contains(startWord)) {
            return 2;
        }
        if (!graph.validWords().contains(endWord)) {
            return 3;
        }

        Queue<Vertex> queue = new LinkedList<>();
        HashSet<Vertex> visited = new HashSet<>();

        Vertex start = graph.map().get(startWord);
        queue.add(start);
        visited.add(start);

        if (startWord.equals(endWord)) {
            return 0;
        }

        while (true) {
            Vertex current = queue.poll();

            if (current == null) {
                return 1;
            }

            for (Vertex adj : current.adjacent()) {
                if (visited.contains(adj)) {
                    continue;
                }

                if (adj.word().equals(endWord)) {
                    parents.put(adj, current);
                    return 0;
                }

                parents.put(adj, current);
                queue.add(adj);
                visited.add(adj);
            }
        }
    }

    @Override
    public String toString() {
        if (parents == null) {
            return null;
        }

        LinkedList<String> trace = new LinkedList<>();

        Vertex current = graph.map().get(endWord);
        while (current != null) {
            trace.push(current.word());
            current = parents.get(current);
        }
        int distance = trace.size() - 1;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < distance; i++) {
            sb.append(trace.pop() + " -->\n");
        }
        sb.append(trace.pop() + " (" + distance + ")");

        return sb.toString();
    }
}
