import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WordGraph {
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final HashSet<String> validWords;
    private final boolean[] operations;
    private final HashMap<String, Vertex> map;

    /**
     * Constructs a WordGraph.
     *
     * @param validWords
     * @param operations
     */
    public WordGraph(HashSet<String> validWords, boolean[] operations) {
        this.validWords = validWords;
        this.operations = operations;
        this.map = new HashMap<>();

        for (String word : validWords) {
            Vertex v = new Vertex(word);
            map.put(word, v);
        }

        for (Vertex v : map.values()) {
            v.setAdjacent(getAdjacent(v.word()));
        }
    }

    public HashSet<String> validWords() {
        return validWords;
    }

    public HashMap<String, Vertex> map() {
        return map;
    }

    private HashSet<Vertex> getAdjacent(String word) {
        ArrayList<Character> chars = new ArrayList<>();
        for (char letter : word.toCharArray()) {
            chars.add(letter);
        }

        HashSet<String> adjacent = new HashSet<>();

        for (int ichar = 0; ichar < chars.size(); ichar++) {
            if (operations[0]) {
                ArrayList<Character> rmAdjacent = new ArrayList<>(chars);
                rmAdjacent.remove(ichar);
                adjacent.add(charArrayToString(rmAdjacent));
            }
            if (operations[1]) {
                for (char letter : alphabet) {
                    ArrayList<Character> setAdjacent = new ArrayList<>(chars);
                    setAdjacent.set(ichar, letter);
                    adjacent.add(charArrayToString(setAdjacent));
                }
            }
        }
        if (operations[2]) {
            for (int ipos = 0; ipos < chars.size() + 1; ipos++) {
                for (char letter : alphabet) {
                    ArrayList<Character> addAdjacent = new ArrayList<>(chars);
                    addAdjacent.add(ipos, letter);
                    adjacent.add(charArrayToString(addAdjacent));
                }
            }
        }

        HashSet<Vertex> validAdjacent = new HashSet<>();
        for (String adj : adjacent) {
            if (validWords.contains(adj)) {
                validAdjacent.add(map.get(adj));
            }
        }
        validAdjacent.remove(map.get(word));
        return validAdjacent;
    }

    private static String charArrayToString(ArrayList<Character> charArray) {
        StringBuilder sb = new StringBuilder();
        for (char letter : charArray) {
            sb.append(letter);
        }
        return sb.toString();
    }
}
