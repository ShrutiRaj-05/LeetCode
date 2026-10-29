
import java.util.*;

class Solution {

    List<Integer>[] graph;
    int[] parent, depth, tin, tout;
    int[] rootXor;
    int[][] up;
    int[] bit;
    char[] chars;
    int timer, LOG, n;

    int charMask(char c) {
        return 1 << (c - 'a');
    }

    public List<Boolean> palindromePath(
        int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        chars = s.toCharArray();

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        parent = new int[n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        rootXor = new int[n];
        timer = 0;

        int[] stackNode = new int[2 * n + 5];
        int[] stackParent = new int[2 * n + 5];
        int[] stackState = new int[2 * n + 5];
        int top = 0;

        stackNode[top] = 0;
        stackParent[top] = -1;
        stackState[top++] = 0;

        while (top > 0) {
            --top;
            int u = stackNode[top];
            int p = stackParent[top];
            int state = stackState[top];

            if (state == 0) {
                parent[u] = (p == -1) ? u : p;
                rootXor[u] = (p == -1)
                        ? charMask(chars[u])
                        : rootXor[p] ^ charMask(chars[u]);

                tin[u] = timer++;

                stackNode[top] = u;
                stackParent[top] = p;
                stackState[top++] = 1;

                for (int v : graph[u]) {
                    if (v != p) {
                        depth[v] = depth[u] + 1;
                        stackNode[top] = v;
                        stackParent[top] = u;
                        stackState[top++] = 0;
                    }
                }
            } else {
                tout[u] = timer;
            }
        }
        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        bit = new int[n + 2];

        List<Boolean> results = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = charMask(chars[u]) ^ charMask(c);

                if (delta != 0) {
                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int mask = currentRootXor(u)
                        ^ currentRootXor(v)
                        ^ charMask(chars[ancestor]);

                results.add((mask & (mask - 1)) == 0);
            }
        }

        return results;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return parent[u];
    }

    // Fenwick tree: point XOR update
    void add(int idx, int delta) {
        idx++;

        while (idx < bit.length) {
            bit[idx] ^= delta;
            idx += idx & -idx;
        }
    }

    void rangeXor(int left, int right, int delta) {
        add(left, delta);
        add(right, delta);
    }

    int pointXor(int idx) {
        int result = 0;
        idx++;

        while (idx > 0) {
            result ^= bit[idx];
            idx -= idx & -idx;
        }

        return result;
    }

    int currentRootXor(int u) {
        return rootXor[u] ^ pointXor(tin[u]);
    }
}
