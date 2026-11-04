import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        // coordinate compression
        int[] vals = Arrays.stream(nums).distinct().sorted().toArray();
        int m = vals.length;
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) idx[i] = Arrays.binarySearch(vals, nums[i]) + 1; // 1-indexed

        int[] cnt = new int[m + 1];
        long[] sum = new long[m + 1];
        int top = Integer.highestOneBit(m);

        int W = n - x + 1;
        long[] cost = new long[W];
        long total = 0;
        int target = (x + 1) / 2;

        for (int i = 0; i < n; i++) {
            // add nums[i]
            for (int p = idx[i]; p <= m; p += p & -p) { cnt[p]++; sum[p] += nums[i]; }
            total += nums[i];

            if (i >= x) { // remove nums[i-x]
                int r = i - x;
                for (int p = idx[r]; p <= m; p += p & -p) { cnt[p]--; sum[p] -= nums[r]; }
                total -= nums[r];
            }

            if (i >= x - 1) {
                // find the target-th smallest via Fenwick descent
                int pos = 0, rem = target;
                long accSum = 0;
                for (int pw = top; pw > 0; pw >>= 1) {
                    int np = pos + pw;
                    if (np <= m && cnt[np] < rem) {
                        pos = np;
                        rem -= cnt[np];
                        accSum += sum[np];
                    }
                }
                long v = vals[pos];               // median value (0-indexed pos == 1-indexed pos+1)
                long cntLess = target - rem;      // elements strictly smaller than v
                long sumLess = accSum;
                cost[i - x + 1] = v * cntLess - sumLess + (total - sumLess) - v * (x - cntLess);
            }
        }

        final long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1]; // j = 0: zero cost
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            Arrays.fill(cur, INF);
            for (int i = 1; i <= n; i++) {
                long best = cur[i - 1];
                if (i >= x && prev[i - x] < INF) {
                    best = Math.min(best, prev[i - x] + cost[i - x]);
                }
                cur[i] = best;
            }
            prev = cur;
        }
        return prev[n];
    }
}