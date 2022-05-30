import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Compression {
    public static String HuffmanCoding(String input) {
        StringBuilder builder = new StringBuilder();

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
        PriorityQueue<TreeNode> queue = new PriorityQueue<>(map.size());

        // Converting pairings to tree nodes and adding it to the priority queue.
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            OccurrenceNode node = new OccurrenceNode(entry.getKey(), entry.getValue());
            queue.add(node);
        }

        PriorityQueue<TreeNode> printQueue = new PriorityQueue<>(queue);
        System.out.println("Entries in order:");
        while (printQueue.size() != 0) {
            System.out.println(printQueue.poll());
        }

        TreeNode root = null;

        // Keep taking the next 2 nodes and create a parents with their sums.
        while (queue.size() > 1) {
            TreeNode x = queue.poll();
            TreeNode y = queue.poll();

            TreeNode n = new TreeNode(x, y, x.value + y.value);
            root = n;

            queue.add(n);
        }


        TreeNode.printCode(root, "");
        return builder.toString();
    }
}
