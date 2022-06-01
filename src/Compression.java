import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import com.thecaister.imageprocessing.ImageProcessing;

public class Compression {
    public static BufferedImage DCTEncoding(BufferedImage image){
        ArrayList<Integer[][]> allChunks = new ArrayList<>();
        // First, we will split the image into 8x8 chunks.
        // Make sure that the image is divisible by 8 through padding.
        // 33 should turn into 40, 23 should turn into 24, etc.
        int initImageWidth;
        int initImageHeight;

        initImageWidth = image.getWidth();
        initImageHeight = image.getHeight();

        int inputX = initImageWidth % 8 == 0 ? initImageWidth : initImageWidth + (8 - (initImageWidth % 8));
        int inputY = initImageHeight % 8 == 0 ? initImageHeight : initImageHeight + (8 - (initImageHeight % 8));
        int maxChunksX = inputX / 8;
        int maxChunksY = inputY / 8;
        int chunkAmount = maxChunksX * maxChunksY;

        // Initialising
        for(int i = 0; i < chunkAmount; i++){
            allChunks.add(new Integer[8][8]);
        }

        // Split into chunks to be placed into "allChunks".
        for(int i = 0; i < inputX; i++){
            for(int j = 0; j < inputY; j++){
                int currentChunkX = i / 8;
                int currentChunkY = j / 8;
                int currentChunkIndex = currentChunkX + (currentChunkY * maxChunksX);

                allChunks.get(currentChunkIndex)[i % 8][j % 8] = image.getRGB(i, j);
            }
        }

        for (int i = 0; i < allChunks.size(); i++) {
            int pixelX = ((i % maxChunksX) * 8);
            int pixelY = ((i / maxChunksY) * 8);
            processDCTChunk(allChunks.get(i), pixelX, pixelY, inputX, inputY);
        }

        // Rebuilding image from "allChunks"
        for(int i = 0; i < allChunks.size(); i++){
            int currentChunkX = i % maxChunksX;
            int currentChunkY = i / maxChunksY;
            for(int j = 0; j < 8; j++){
                for(int k = 0; k < 8; k++){
                    int pixelX = (currentChunkX * 8) + j;
                    int pixelY = (currentChunkY * 8) + k;
                    image.setRGB(pixelX, pixelY, allChunks.get(i)[j][k]);
                }
            }
        }

        return image;
    }

    public static void processDCTChunk(Integer[][] chunk, int chunkX, int chunkY, int imageWidth, int imageHeight){
        double pi = Math.PI;
        double bpq, ap, aq, amn;
        int imageX, imageY;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                imageX = chunkX + i;
                imageY = chunkY + j;

                chunk[i][j] -= 0x80000000;

                // Calculating ap and aq
                if(imageX == 0){
                    ap = 1 / Math.sqrt(imageWidth);
                }else{
                    ap = Math.sqrt(2 / imageWidth);
                }

                if(imageY == 0){
                    aq = 1 / Math.sqrt(imageHeight);
                }else{
                    aq = Math.sqrt(2 / imageHeight);
                }
            }
        }

        return;
    }

    /**
     * @param input The original message that you want to encode.
     * @return The encoded form of the input.
     */
    public static String HuffmanEncoding(String input) {
        HuffmanNode.currentWorkingHuffmanTreeNode = null;
        HuffmanNode.currentWorkingHuffmanTable = new HashMap<>();

        StringBuilder output = new StringBuilder();

        // First, make a map of chars with their amounts.
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (map.containsKey(current)) {
                map.put(current, map.get(current) + 1);
            } else {
                map.put(current, 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.printf("%c\t:\t%d\n", entry.getKey(), entry.getValue());
        }

        // Place the nodes entries of the HashMap into a Priority Queue so that they
        // can be sorted by value.
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(map.size());

        // Converting pairings to tree nodes and adding it to the priority queue.
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            OccurrenceNode node = new OccurrenceNode(entry.getKey(), entry.getValue());
            queue.add(node);
        }

        PriorityQueue<HuffmanNode> printQueue = new PriorityQueue<>(queue);
        System.out.println("Entries in order:");
        while (printQueue.size() != 0) {
            System.out.println(printQueue.poll());
        }

        // Now, we start the generation of the tree, starting with an empty "root" node.
        HuffmanNode root = null;

        // Keep taking the next 2 nodes and create a parent node with their sums.
        // Add this parent to the queue as well, constantly updating "root" along the way.
        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();

            HuffmanNode n = new HuffmanNode(x, y, x.value + y.value);
            root = n;

            queue.add(n);
        }

        HuffmanNode.printCode(root, "");

        generateHuffmanTable(root, "");

        HuffmanNode.currentWorkingHuffmanTreeNode = root;

        // Start the encoding process once we have the Huffman table prepared, appending
        // the codes to the output and then returning it.
        HashMap<Character, String> huffmanTable = HuffmanNode.currentWorkingHuffmanTable;

        for (int i = 0; i < input.length(); i++) {
            output.append(huffmanTable.get(input.charAt(i)));
        }

        return output.toString();
    }

    /**
     * Decodes an encoded Huffman message.
     *
     * @param input Encoded Huffman message to be decoded.
     * @return The decoded message.
     */
    public static String HuffmanDecoding(String input) {
        // Getting a reference to the root of the tree because we'll be coming back
        // here numerous times as we decode more characters.
        HuffmanNode root = HuffmanNode.currentWorkingHuffmanTreeNode;

        // Using "currentNode" to traverse the Huffman Tree.
        HuffmanNode currentNode = root;
        StringBuilder output = new StringBuilder();


        for (int i = 0; i < input.length(); i++) {
            // Then, we go to either the left/right children depending on what the encoded message says.
            if (input.charAt(i) == '0') {
                currentNode = currentNode.getLeft();
            } else if (input.charAt(i) == '1') {
                currentNode = currentNode.getRight();
            }

            // If we hit an "OccurrenceNode", add its character to the output and continue.
            if (currentNode instanceof OccurrenceNode) {
                output.append(((OccurrenceNode) currentNode).getCharacter());
                currentNode = root;
            }
        }

        return output.toString();

    }

    /**
     * Generates the Huffman table using the provided root and an empty String.
     *
     * @param root The root of the Huffman Tree.
     * @param code The built-up code of a character so far. Use "" as the input for the first call of the method.
     */
    private static void generateHuffmanTable(HuffmanNode root, String code) {
        HashMap<Character, String> map = HuffmanNode.currentWorkingHuffmanTable;

        if (root instanceof OccurrenceNode) {
            char current = ((OccurrenceNode) root).getCharacter();

            if (map.containsKey(current)) {
                map.put(current, map.get(current) + code);
            } else {
                map.put(current, code);
            }

            System.out.println(((OccurrenceNode) root).getCharacter() + "   |  " + code);
            return;
        }

        generateHuffmanTable(root.left, code + "0");
        generateHuffmanTable(root.right, code + "1");
    }

    /**
     * Calculating efficiency of Huffman coding for a single instance.
     *
     * @param input Message to be used for benchmarking.
     * @return The percentage of the encoded message size.
     */
    public static float calculateHuffmanEfficiency(String input) {
        // Java chars take up 16 bits.
        int lengthInput = input.length() * 16;
        int lengthOutput;
        String encodedVersion = HuffmanEncoding(input);
        lengthOutput = encodedVersion.length();

        int difference = lengthInput - lengthOutput;
        float percentage = 100 * (float) lengthOutput / lengthInput;

        System.out.printf("Length of the message without encoding: %d\n", lengthInput);
        System.out.printf("Length of the message after encoding: %d\n", lengthOutput);

        System.out.printf("The encoded message is %.2f%% the size of the original message. This is assuming that characters take up 16 bits.\n", percentage);
        System.out.printf("You have saved this many bits: %d.\n", difference);

        return percentage;
    }

    /**
     * Calculating average statistics for Huffman coding using Strings of random lengths in a range
     * and repeated for a certain amount of iterations.
     *
     * @param minimumSize Minimum input message size range.
     * @param maximumSize Maximum input message size range.
     * @param iterations  The amount of times to generate a random String in the length range and process it,
     *                    contributing to the overall averages.
     */
    public static void calculateAverageHuffmanStatistics(int minimumSize, int maximumSize, int iterations) {
        int lengthOriginalMessagesSum = 0;
        int lengthEncodedMessagesSum = 0;
        float percentagesReducedAverage;
        float lengthAverageOriginal;
        float lengthAverageEncoded;

        for (int i = 0; i < iterations; i++) {
            int randomSize = (int) (Math.random() * (maximumSize - minimumSize)) + minimumSize;
            String currentInput = InputGenerator.GenerateRandomText(randomSize);
            lengthOriginalMessagesSum += currentInput.length() * 16;
            lengthEncodedMessagesSum += Compression.HuffmanEncoding(currentInput).length();
        }

        lengthAverageOriginal = (float) lengthOriginalMessagesSum / iterations;
        lengthAverageEncoded = (float) lengthEncodedMessagesSum / iterations;
        percentagesReducedAverage = 100 * ((float) lengthOriginalMessagesSum - lengthEncodedMessagesSum) / lengthOriginalMessagesSum;

        System.out.printf("Average length of original messages: %f\n", lengthAverageOriginal);
        System.out.printf("Average length of encoded messages: %f\n", lengthAverageEncoded);
        System.out.printf("Average percentage reduced: %f%%\n", percentagesReducedAverage);

    }
}
