class Solution {

    static class Node {
        char leftChar, rightChar;
        int prefix, suffix, best, len;

        Node(char leftChar, char rightChar, int prefix,
             int suffix, int best, int len) {
            this.leftChar = leftChar;
            this.rightChar = rightChar;
            this.prefix = prefix;
            this.suffix = suffix;
            this.best = best;
            this.len = len;
        }
    }

    Node[] tree;
    char[] arr;

    // Merge two segment tree nodes
    private Node merge(Node a, Node b) {

        if (a == null) return b;
        if (b == null) return a;

        Node res = new Node(
            a.leftChar,
            b.rightChar,
            a.prefix,
            b.suffix,
            Math.max(a.best, b.best),
            a.len + b.len
        );

        // If boundary characters are the same,
        // prefix/suffix/best may extend across the boundary.
        if (a.rightChar == b.leftChar) {

            // Entire left segment has the same character
            if (a.prefix == a.len) {
                res.prefix = a.len + b.prefix;
            }

            // Entire right segment has the same character
            if (b.suffix == b.len) {
                res.suffix = b.len + a.suffix;
            }

            // A new best substring can cross the boundary
            res.best = Math.max(
                res.best,
                a.suffix + b.prefix
            );
        }

        return res;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(
                arr[l],
                arr[l],
                1,
                1,
                1,
                1
            );
            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    private void update(int node, int l, int r, int idx, char c) {

        if (l == r) {
            tree[node] = new Node(
                c, c,
                1, 1, 1, 1
            );
            return;
        }

        int mid = l + (r - l) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, c);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, c);
        }

        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {

        int n = s.length();
        int k = queryCharacters.length();

        arr = s.toCharArray();

        tree = new Node[4 * n];

        // Build tree
        build(1, 0, n - 1);

        int[] lengths = new int[k];

        for (int i = 0; i < k; i++) {

            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);

            arr[idx] = c;

            // Update only the affected path
            update(1, 0, n - 1, idx, c);

            // Root contains answer for entire string
            lengths[i] = tree[1].best;
        }

        return lengths;
    }
}