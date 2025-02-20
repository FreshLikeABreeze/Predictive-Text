package textautofill;

public class DLBNode {

    private char value;

    private DLBNode right;

    private DLBNode down;

    // initializer
    public DLBNode(char value) {
        this.value = value;

        // give new nodes no attached nodes
        this.right = null;
        this.down = null;
    }

    // setters
    public void setRight(DLBNode right) {
        this.right = right;
    }

    public void setDown(DLBNode down) {
        this.down = down;
    }

    // getters
    public char getValue() {
        return value;
    }

    public DLBNode getRight() {
        return right;
    }

    public DLBNode getDown() {
        return down;
    }

}