class Solution {
    public int numberOfSets(int n, int k) {
        final long MOD = 1000000007;

        int max = n + k - 1;
        long[][] dp = new long[max + 1][2 * k + 1];

        // dp[i][j] = C(i, j)
        for (int i = 0; i <= max; i++) {
            dp[i][0] = 1;

            for (int j = 1; j <= Math.min(i, 2 * k); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        return (int) dp[max][2 * k];
    }
}