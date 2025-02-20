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
        // start at root
        // check one down for first letter
        // if not, go right until found letter
        // if found letter go down and repeat
        // else insert nodes to finnish the word

        DLBNode currNode = root;
        word = word + '^';

        for (int i = 0; i < word.length(); i++) {
            //
            System.out.println(word.charAt(i));
            //
            char letter = word.charAt(i);
            currNode = insertNode(currNode, letter);
        }
    }

    // recursive helper for inserting a node
    private DLBNode insertNode(DLBNode node, char letter) {
        DLBNode currNode = node;
        DLBNode prevNode = null;

        // Traverse through the same level
        while (currNode != null && currNode.getValue() != letter) {
            prevNode = currNode;
            currNode = currNode.getRight();
        }

        // If node doesnt exist, create a new node
        if (currNode == null) {
            currNode = new DLBNode(letter);
            if (prevNode != null) {
                prevNode.setRight(currNode);
            }
        }

        // Move down the end of the character sequence
        if (currNode.getDown() == null) {
            if (letter == '^') {
                currNode.setDown(new DLBNode('^'));
            } else {
                currNode.setDown(new DLBNode('^'));
            }
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
        // end of pre reached
        return true;
    }

    // get list of suggestions and return the top 5 in another list
    public ArrayList<String> suggest() {
        ArrayList<String> suggestions = new ArrayList<>();

        DLBNode currNode = root;

        String prefix = "";
        while (currNode != null && currNode.getValue() != '^') {
            prefix += currNode.getValue();
            currNode = currNode.getDown();
        }

        suggestRec(currNode, prefix, suggestions);
        Collections.sort(suggestions);

        ArrayList<String> suggest5 = new ArrayList<>();
        for (int i = 0; i < suggestions.size() && i < 5; i++) {
            suggest5.add(suggestions.get(i));
        }

        return suggest5;

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
        while (checking != null) {

            // Check for the end marker '^'
            if (checking.getValue() == '^') {
                list.add(currentWord);
            } else {
                traverseRec(checking.getDown(), currentWord + checking.getValue(), list);
            }

            checking = checking.getRight();
        }

        // When you reach max depth, move to right nodes
        traverseRec(currNode.getRight(), currentWord, list);
    }

}
