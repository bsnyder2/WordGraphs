import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WordGraph {
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private HashSet<String> validWords;
    private HashMap<String, Vertex> map;

    public WordGraph(HashSet<String> validWords) {
        this.validWords = validWords;
        this.map = new HashMap<>();

        for (String word : validWords) {
            Vertex v = new Vertex(word);
            map.put(word, v);
            v.addAllAdjacent(getAdjacent(word));
        }
    }

    private HashSet<Vertex> getAdjacent(String word) {
        ArrayList<Character> chars = new ArrayList<>();
        for (char letter : word.toCharArray()) {
            chars.add(letter);
        }

        HashSet<String> adjacent = new HashSet<>();

        for (int ichar = 0; ichar < chars.size(); ichar++) {
            ArrayList<Character> rmAdjacent = new ArrayList<>(chars);
            rmAdjacent.remove(ichar);
            adjacent.add(charArrayToString(rmAdjacent));
            for (char letter : alphabet) {
                ArrayList<Character> setAdjacent = new ArrayList<>(chars);
                setAdjacent.set(ichar, letter);
                adjacent.add(charArrayToString(setAdjacent));
            }
        }
        for (int ipos = 0; ipos < chars.size() + 1; ipos++) {
            for (char letter : alphabet) {
                ArrayList<Character> addAdjacent = new ArrayList<>(chars);
                addAdjacent.add(ipos, letter);
                adjacent.add(charArrayToString(addAdjacent));
            }
        }

        HashSet<Vertex> validAdjacent = new HashSet<>();
        for (String adj : adjacent) {
            if (validWords.contains(adj)) {
                validAdjacent.add(map.get(adj));
            }
        }

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