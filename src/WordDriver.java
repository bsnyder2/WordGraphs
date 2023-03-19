import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class WordDriver {
    /**
     * Loads all words from a given file into a HashSet.
     * 
     * @return validWords
     */
    private static HashSet<String> loadWords(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        HashSet<String> validWords = new HashSet<>();
        while (scanner.hasNext()) {
            validWords.add(scanner.next());
        }
        scanner.close();

        return validWords;
    }

    public static void main(String[] args) {
        String filename = "../wordsets/words-58k.txt";
        HashSet<String> validWords = loadWords(filename);

        boolean[] ops = { true, false, true };
        WordGraph g = new WordGraph(validWords, ops);

        String startWord = "treats";
        String[] words = { "tea", "rest", "reasons", "science" };
        for (int i = 0; i < words.length; i++) {
            WordTrace t = new WordTrace(g, startWord, words[i]);
            t.BFS();
            System.out.println(t.toString() + "\n");
        }
    }
}