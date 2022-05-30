public class Driver {
    enum CompressionType{
        HUFFMAN,
        DCT,
        BLOCK_MOTION
    }

    public static void runCompression(CompressionType type){
        switch(type){
            case DCT -> System.out.println("Running the Discrete Cosine Transformation...");
            case HUFFMAN -> System.out.println("Running Huffman Coding...");
            case BLOCK_MOTION -> System.out.println("Running Block Motion compression...");

            default -> {
                return;
            }
        }
    }

    public static void main(String[] args) {
        CompressionType type = CompressionType.DCT;

        InputGenerator.GenerateRandomText(100);
        //runCompression(type);
    }
}
