package textautofill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    // reads dictionary file and adds all words to the DLBTrie
    private static void readFile(DLBTrie trie, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (!word.isEmpty()) {
                    System.out.println("PTTING IT IN DLB");
                    trie.add(word);
                }
            }
        } catch (IOException e) {
            // print error message
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("HELLO");
        DLBTrie trie = new DLBTrie();
        History hist = new History();

        try {
            readFile(trie,
                    "C:/Users/Nano/Desktop/Personal Project/TextAutoFIll/app/build/resources/main/my_words.txt");
        } catch (Exception e) {
            // print error message
            e.printStackTrace();
        }
        System.out.println("ITS READ");

        ArrayList<String> allWords = trie.traverse();
        System.out.println(allWords);

        

        System.out.println("ENDING");

    }
}
