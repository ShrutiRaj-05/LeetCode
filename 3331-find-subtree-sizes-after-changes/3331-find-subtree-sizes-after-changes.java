import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        // 1. Build the original tree
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            tree.get(parent[i]).add(i);
        }

        // Stores the new parent of every node
        int[] newParent = parent.clone();

        // Tracks the nearest ancestor for each character
        int[] last = new int[26];
        Arrays.fill(last, -1);

        // Stores the previous last occurrence for restoration
        int[] previousLast = new int[n];

        // Iterative DFS: enter and exit events
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int v = stack.pop();

            // Exit event
            if (v < 0) {
                int node = ~v;
                last[s.charAt(node) - 'a'] = previousLast[node];
                continue;
            }

            int c = s.charAt(v) - 'a';

            // Find nearest matching ancestor in original tree
            if (v != 0 && last[c] != -1) {
                newParent[v] = last[c];
            }

            // Save previous occurrence and update current occurrence
            previousLast[v] = last[c];
            last[c] = v;

            // Push exit event
            stack.push(~v);

            // Push children (reverse order is optional)
            for (int i = tree.get(v).size() - 1; i >= 0; i--) {
                stack.push(tree.get(v).get(i));
            }
        }

        // 2. Build the modified tree
        List<List<Integer>> finalTree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            finalTree.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            finalTree.get(newParent[i]).add(i);
        }

        // 3. Calculate subtree sizes
        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        // Create traversal order of final tree
        List<Integer> order = new ArrayList<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int v = stack.pop();
            order.add(v);

            for (int child : finalTree.get(v)) {
                stack.push(child);
            }
        }

        // Process children before their parents
        for (int i = order.size() - 1; i > 0; i--) {
            int node = order.get(i);
            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}