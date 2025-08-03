class Solution {
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            // ans[i >> 1] gives count of i/2, i & 1 adds 1 if i is odd
            ans[i] = ans[i >> 1] + (i & 1);
        }

        return ans;

    }
}