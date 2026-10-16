
class Solution {
    static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int[] last = new int[100001];

        SegmentTree st = new SegmentTree(n);
        long ans = 0;

        for (int r = 0; r < n; r++) {
            int x = nums[r];
            int prev = last[x];

            // Increase distinct counts for valid subarrays
            st.update(prev, r, 1);

            // Sum of squared distinct counts for subarrays ending at r
            ans = (ans + st.query(0, r)) % MOD;

            last[x] = r + 1;
        }

        return (int) ans;
    }

    static class SegmentTree {
        int n;
        long[] sum;
        long[] sq;
        long[] lazy;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            sq = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int l, int r, long v) {
            long len = r - l + 1L;

            sq[node] = (sq[node] + 2 * v % MOD * sum[node] % MOD
                       + len % MOD * v % MOD * v % MOD) % MOD;

            sum[node] = (sum[node] + len * v) % MOD;
            lazy[node] = (lazy[node] + v) % MOD;
        }

        void push(int node, int l, int r) {
            if (lazy[node] == 0 || l == r) return;

            int mid = (l + r) / 2;
            apply(node * 2, l, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, r, lazy[node]);

            lazy[node] = 0;
        }

        void update(int ql, int qr, long v) {
            if (ql > qr) return;
            update(1, 0, n - 1, ql, qr, v);
        }

        void update(int node, int l, int r,
                    int ql, int qr, long v) {
            if (ql <= l && r <= qr) {
                apply(node, l, r, v);
                return;
            }

            push(node, l, r);
            int mid = (l + r) / 2;

            if (ql <= mid)
                update(node * 2, l, mid, ql, qr, v);

            if (qr > mid)
                update(node * 2 + 1, mid + 1, r, ql, qr, v);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
        }

        long query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        long query(int node, int l, int r,
                   int ql, int qr) {
            if (ql <= l && r <= qr)
                return sq[node];

            push(node, l, r);
            int mid = (l + r) / 2;
            long res = 0;

            if (ql <= mid)
                res = query(node * 2, l, mid, ql, qr);

            if (qr > mid)
                res = (res + query(node * 2 + 1,
                                   mid + 1, r, ql, qr)) % MOD;

            return res;
        }
    }
}
