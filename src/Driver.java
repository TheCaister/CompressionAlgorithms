public class Driver {
    enum CompressionType{
        HUFFMAN,
        DCT,
        BLOCK_MOTION
    }

    /**
     * Runs different compression algorithms based on the type the user passes in.
     * @param type
     */
    public static void runCompression(CompressionType type){
        switch(type){
            case DCT -> System.out.println("Running the Discrete Cosine Transformation...");
            case HUFFMAN -> System.out.println("Running Huffman Coding...");
            case BLOCK_MOTION -> System.out.println("Running Block Motion compression...");

            default -> {}
        }
    }

    public static void main(String[] args) {
        CompressionType type = CompressionType.DCT;

        String input = InputGenerator.GenerateRandomText(5);

        OccurrenceNode o1 = new OccurrenceNode('a', 3);
        OccurrenceNode o2 = new OccurrenceNode('b', 2);
        OccurrenceNode o3 = new OccurrenceNode('c', 5);
        OccurrenceNode o4 = new OccurrenceNode('d', 7);

        TreeNode n1 = new TreeNode(o1, o2);
        TreeNode n2 = new TreeNode(o3, o4);
        TreeNode root = new TreeNode(n1, n2);

        System.out.println(input);
        Compression.HuffmanCoding("aaabbc");

        //System.out.println(root.calculateValue());

        //runCompression(type);
    }
}
