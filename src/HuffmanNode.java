import java.util.HashMap;

public class HuffmanNode implements Comparable<HuffmanNode> {

    // Maintain a global table and root for the Huffman Tree.
    public static HuffmanNode currentWorkingHuffmanTreeNode = null;
    public static HashMap<Character, String> currentWorkingHuffmanTable = new HashMap<>();

    protected HuffmanNode left = null;
    protected HuffmanNode right = null;
    protected int value = 0;

    public HuffmanNode(int value) {
        this.value = value;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }


    // Recursive function for calculating a node's value.
    // Adds up value from every child variable.
    public int calculateValue() {
        int leftValue = 0;
        int rightValue = 0;

        // Stopping condition
        // First, it checks the left child to see if there's a node. If there's a node, call the calculateValue function on it for the recursive element.
        // If the left is null, we move on to the right node and repeat.
        // If both are null(current node is a leaf), return its value and propagate up.
        if (left == null && right == null) {
            return value;
        }

        if (left != null) {
            leftValue = left.calculateValue();
        }

        if (right != null) {
            rightValue = right.calculateValue();
        }

        return leftValue + rightValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Value: " + value;
    }

    /**
     * Printing out character with their respective codes.
     *
     * @param root The root node of the Huffman Tree.
     * @param code The code of a character. This will be built up during the recursive calls.
     */
    public static void printCode(HuffmanNode root, String code) {
        if (root instanceof OccurrenceNode) {

            System.out.println(((OccurrenceNode) root).getCharacter() + "   |  " + code);

            return;
        }

        printCode(root.left, code + "0");
        printCode(root.right, code + "1");
    }
}

/**
 * Stores a character and how many times it has occurred.
 */
class OccurrenceNode extends HuffmanNode {

    private char character;

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public OccurrenceNode(char c, int amount) {
        super(amount);
        this.character = c;
    }

    @Override
    public String toString() {
        return "Char: " + character + "\t\tValue: " + value;
    }
}
