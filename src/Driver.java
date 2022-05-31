import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

        InputGenerator.GenerateRandomText(1000, "test");
        String input = "cztqs";

        try{
            String fileName = "test";
            File file = new File("inputs/" + fileName + ".txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                input = reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        OccurrenceNode o1 = new OccurrenceNode('a', 3);
        OccurrenceNode o2 = new OccurrenceNode('b', 2);
        OccurrenceNode o3 = new OccurrenceNode('c', 5);
        OccurrenceNode o4 = new OccurrenceNode('d', 7);

        HuffmanNode n1 = new HuffmanNode(o1, o2);
        HuffmanNode n2 = new HuffmanNode(o3, o4);
        HuffmanNode root = new HuffmanNode(n1, n2);

        //System.out.println(input);
        Compression.HuffmanCoding(input);

        //System.out.println(root.calculateValue());

        //runCompression(type);
    }
}
