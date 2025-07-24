import java.util.*;

public class Solution {
    static class Edge {
        int from, to;
        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    List<Integer>[] tree;
    int[] nums, xor, inTime, outTime;
    int time = 0;

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        this.nums = nums;
        tree = new ArrayList[n];
        xor = new int[n];
        inTime = new int[n];
        outTime = new int[n];

        for (int i = 0; i < n; i++)
            tree[i] = new ArrayList<>();

        for (int[] e : edges) {
            tree[e[0]].add(e[1]);
            tree[e[1]].add(e[0]);
        }

        dfs(0, -1);

        int totalXor = xor[0];
        int minScore = Integer.MAX_VALUE;

        List<Edge> directedEdges = new ArrayList<>();

        // Turn edges into child -> parent (directed from child to parent in DFS tree)
        for (int[] e : edges) {
            if (isAncestor(e[0], e[1])) {
                directedEdges.add(new Edge(e[1], e[0]));
            } else {
                directedEdges.add(new Edge(e[0], e[1]));
            }
        }

        // Try all pairs of directed edges
        for (int i = 0; i < directedEdges.size(); i++) {
            for (int j = i + 1; j < directedEdges.size(); j++) {
                Edge e1 = directedEdges.get(i);
                Edge e2 = directedEdges.get(j);

                int x = xor[e1.from];
                int y = xor[e2.from];

                if (isAncestor(e1.from, e2.from)) {
                    // e1 is ancestor of e2 → component 1: e2, component 2: e1 - e2, component 3: rest
                    y = xor[e2.from];
                    x = xor[e1.from] ^ xor[e2.from];
                } else if (isAncestor(e2.from, e1.from)) {
                    // e2 is ancestor of e1 → component 1: e1, component 2: e2 - e1, component 3: rest
                    x = xor[e1.from];
                    y = xor[e2.from] ^ xor[e1.from];
                }

                int z = totalXor ^ x ^ y;
                int max = Math.max(x, Math.max(y, z));
                int min = Math.min(x, Math.min(y, z));
                minScore = Math.min(minScore, max - min);
            }
        }

        return minScore;
    }

    // DFS to compute XORs and in/out time
    private void dfs(int node, int parent) {
        inTime[node] = time++;
        xor[node] = nums[node];

        for (int nei : tree[node]) {
            if (nei != parent) {
                dfs(nei, node);
                xor[node] ^= xor[nei];
            }
        }

        outTime[node] = time++;
    }

    // Check if u is ancestor of v
    private boolean isAncestor(int u, int v) {
        return inTime[u] <= inTime[v] && outTime[u] >= outTime[v];
    }
}
