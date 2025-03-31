package textautofill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    // reads dictionary file and adds all words to the DLBTrie
    private static void readFile(DLBTrie trie, History hist, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;

            while ((word = reader.readLine()) != null) {
                word = word.trim();

                // Add words to both data structures
                if (!word.isEmpty()) {
                    hist.add(word);
                    trie.add(word);
                }
            }
        } catch (IOException e) {
            // Print error message
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        DLBTrie trie = new DLBTrie();
        History hist = new History();

        try {
            readFile(trie, hist,
                    "C:/Users/Nano/Desktop/Personal Project/TextAutoFIll/app/build/resources/main/words_alpha.txt");
        } catch (Exception e) {
            // Print error message
            e.printStackTrace();
        }
        System.out.println("ITS READ");

        // ArrayList<String> allWords = trie.traverse();
        // System.out.println(allWords);

        /*
         * Simulated inputs:
         * 
         * b
         * br
         * bro
         * brow
         * 
         * 
         */
        System.out.println("Input: \"b\"");
        // System.out.println("hist: " + hist.suggest("b"));
        System.out.println("trie: " + trie.suggest("b") + "\n");

        System.out.println("Input: \"br\"");
        System.out.println("hist: " + hist.suggest("br"));
        System.out.println("trie: " + trie.suggest("br") + "\n");

        System.out.println("Input: \"bro\"");
        System.out.println("hist: " + hist.suggest("bro"));
        System.out.println("trie: " + trie.suggest("bro") + "\n");

        System.out.println("Input: \"brow\"");
        System.out.println("hist: " + hist.suggest("brow"));
        System.out.println("trie: " + trie.suggest("brow") + "\n");

        System.out.println("ENDING");

    }
}
