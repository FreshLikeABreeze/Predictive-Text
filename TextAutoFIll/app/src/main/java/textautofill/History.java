package textautofill;

import java.util.*;

public class History implements Dictionary {

    HashMap<String, Integer> hist;

    public History() {
        hist = new HashMap<>();
    }

    public void add(String key) {
        hist.put(key, hist.getOrDefault(key, 0) + 1);
    }

    public boolean contains(String key) {
        return hist.containsKey(key);
    }

    public boolean containsPrefix(String pre) {
        for (String key : hist.keySet()) {
            if (key.startsWith(pre)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> suggest(String prefix) {
        ArrayList<String> suggestions = new ArrayList<>();

        for (String word : hist.keySet()) {
            if (word.startsWith(prefix)) {
                suggestions.add(word);
            }
        }

        // Alphabetically sort
        Collections.sort(suggestions);

        if (suggestions.size() > 5) {
            ArrayList<String> suggest5 = new ArrayList<>(suggestions.subList(0, 5));
            return suggest5;
        } else {
            return suggestions;
        }
    }

    public ArrayList<String> traverse() {
        ArrayList<String> words = new ArrayList<>(hist.keySet());
        Collections.sort(words);
        return (words);

    }

    public int count() {
        return hist.size();
    }

}
