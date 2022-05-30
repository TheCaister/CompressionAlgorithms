public class TreeNode {
    protected TreeNode left = null;
    protected TreeNode right = null;
    protected int value = 0;

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode(TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
    }
    public TreeNode(TreeNode left, TreeNode right, int value) {
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
        if(left == null && right == null){
            return value;
        }

        if(left != null){
            leftValue = left.calculateValue();
        }

        if(right != null){
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


    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}

/**
 * Stores a character and how many times it has occurred.
 */
class OccurrenceNode extends TreeNode {

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
}
