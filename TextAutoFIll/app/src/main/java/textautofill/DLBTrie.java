package textautofill;

import java.util.*;

public class DLBTrie implements Dictionary {

    private DLBNode root;

    // contructor method creates DLB with root node
    public DLBTrie() {
        this.root = new DLBNode('\0');
    }

    // add new node to DLB Trie
    public void add(String word) {
        /*
         * start at root
         * check one down for first letter
         * if not, go right until found letter
         * if found letter go down and repeat
         * else insert nodes to finnish the word
         */

        DLBNode currNode = root;

        // append end character
        word = word + '^';

        for (int i = 0; i < word.length(); i++) {
            //
            // System.out.println(word.charAt(i));
            //
            char letter = word.charAt(i);

            currNode = insertNode(currNode, letter);
        }
    }

    // Helper for inserting a node
    private DLBNode insertNode(DLBNode node, char letter) {

        // If down is null, we are not at the end of the word
        if (node.getDown() == null) {

            // Insert letter down
            node.setDown(new DLBNode(letter));
            return node.getDown();
        }

        DLBNode currNode = node.getDown();
        DLBNode prevNode = null;

        // Traverse right looking if letter is already on that level
        while (currNode != null && currNode.getValue() != letter) {
            prevNode = currNode;
            currNode = currNode.getRight();
        }

        // If letter not already on level, insert
        if (currNode == null) {
            currNode = new DLBNode(letter);
            prevNode.setRight(currNode);
        }

        return currNode;
    }

    // checks if DLB Trie contains a word
    public boolean contains(String word) {
        return containsHelper(root, word, 0);
    }

    // recursivly traverses through the wanted word
    private boolean containsHelper(DLBNode currNode, String word, int index) {
        if (index == word.length()) {
            return currNode.getDown() == null;
        }

        if (currNode.getDown() != null && currNode.getDown().getValue() == word.charAt(index)) {
            return containsHelper(currNode.getDown(), word, index + 1);
        }

        // Traverse right nodes until we find a match or reach the end
        DLBNode rightNode = currNode.getDown();
        while (rightNode != null) {
            if (rightNode.getValue() == word.charAt(index)) {
                return containsHelper(rightNode, word, index + 1);
            }
            rightNode = rightNode.getRight();
        }

        // If not found, return false
        return false;
    }

    public boolean containsPrefix(String pre) {
        DLBNode currNode = root;

        for (int i = 0; i < pre.length(); i++) {
            char letter = pre.charAt(i);

            // Traverse through the right nodes to find the letter
            while (currNode != null && currNode.getValue() != letter) {
                currNode = currNode.getRight();
            }

            // not found
            if (currNode == null) {
                return false;
            }

            currNode = currNode.getDown();

        }
        // End of pre reached
        return true;
    }

    // Get list of top 5 suggestions
    public ArrayList<String> suggest(String prefix) {
        ArrayList<String> suggestions = new ArrayList<>();

        // Traverse to the node corresponding to the last character of the prefix
        DLBNode curr = findNode(prefix);

        //
        // curr = findCharAtLevel(root.getDown(), 'b');
        //

        if (curr == null) {
            System.out.println("not found");
            return suggestions;
        }

        System.out.println("LAST LETTER OF PREFIX: " + curr.getValue());

        // Find suggestions starting from this node
        suggestRec(curr, prefix, suggestions);

        // Sort suggestions alphabetically
        Collections.sort(suggestions);

        // Return the first 5 suggestions
        return new ArrayList<>(suggestions.subList(0, Math.min(suggestions.size(), 5)));

    }

    // recursive suggestion finding in dlb
    private void suggestRec(DLBNode currNode, String word, ArrayList<String> suggest) {

        if (currNode == null) {
            return;
        }
        if (currNode.getValue() == '^') {
            suggest.add(word.substring(0, word.length() - 1));
        }

        currNode = currNode.getDown();
        while (currNode != null) {
            suggestRec(currNode, word + currNode.getValue(), suggest);
            currNode = currNode.getRight();
        }
    }

    // Finds a prefix and returns last character node
    private DLBNode findNode(String prefix) {
        DLBNode curr = root.getDown();

        for (int i = 0; i < prefix.length(); i++) {
            curr = findCharAtLevel(curr, prefix.charAt(i));

            if (curr == null) {
                return null;
            }
            if (curr.getDown().getValue() != '^') {
                curr = curr.getDown();
            }

        }
        System.out.println("found node value" + curr.getValue());
        return curr;

    }

    // Finds node on a level
    private DLBNode findCharAtLevel(DLBNode node, char ch) {
        while (node != null) {
            // System.out.println("checking: " + node.getValue());
            if (node.getValue() == ch) {
                return node;
            }
            node = node.getRight();
        }
        return null;
    }

    // puts the current DLBTrie into arraylists
    public ArrayList<String> traverse() {
        ArrayList<String> list = new ArrayList<>();
        traverseRec(root, "", list);
        return list;
    }

    // depth first search for words in the DLB
    private void traverseRec(DLBNode currNode, String currentWord, ArrayList<String> list) {
        if (currNode == null) {
            return;
        }

        // System.out.println("currNode");
        // System.out.println(currNode);

        // check for '^' on the whole row
        DLBNode checking = currNode;

        // Check for the end marker '^'
        if (checking.getValue() == '^') {
            list.add(currentWord);
            // System.out.println("CURERNT WHOLE FOUND WORD" + currentWord);
        } else {
            traverseRec(checking.getDown(), currentWord + checking.getValue(), list);
        }

        // When you reach max depth, move to right nodes
        traverseRec(currNode.getRight(), currentWord, list);
    }

}
