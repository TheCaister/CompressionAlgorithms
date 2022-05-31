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
     * @param type Type of compression the user wishes to run.
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

        // Generate some sample files.
        InputGenerator.GenerateRandomText(1000, "test");
        String input = "";

        // Assigning "input" to the text in the file.
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

        input = "Hello there. My name is Jonathan. How are you doing today?";

        // Testing out Huffman encoding and decoding.
        String encodedHuffmanMessage = Compression.HuffmanEncoding(input);
        System.out.println(encodedHuffmanMessage);
        System.out.println(Compression.HuffmanDecoding(encodedHuffmanMessage));
    }
}
