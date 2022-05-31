import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Compression {
    /**
     *
     * @param input
     * @return The encoded form of the input
     */
    public static String HuffmanCoding(String input) {
        // First, make a map of chars with their amounts
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

        // Map of char/int pairings.
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

        HuffmanNode root = null;

        // Keep taking the next 2 nodes and create a parents with their sums.
        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();

            HuffmanNode n = new HuffmanNode(x, y, x.value + y.value);
            root = n;

            queue.add(n);
        }

        HuffmanNode.printCode(root, "");
        return "";
    }

    private void generateHuffmanEncoded(HuffmanNode root, String s){
        HashMap<Character, String> map = HuffmanNode.currentWorkingHuffmanTable;

        if (root instanceof OccurrenceNode) {
            char current = ((OccurrenceNode) root).getCharacter();

            if(map.containsKey(current)){
                map.put(current, map.get(current) + s);
            } else {
                map.put(current, s);
            }

            System.out.println(((OccurrenceNode) root).getCharacter() + "   |  " + s);
            return;
        }

        generateHuffmanEncoded(root.left, s + "0");
        generateHuffmanEncoded(root.right, s + "1");
    }
}
