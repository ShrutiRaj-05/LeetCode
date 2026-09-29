class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int length = m + n - 1;

        // A valid parentheses string must have even length.
        if (length % 2 != 0) {
            return false;
        }

        // First cell must be '('.
        if (grid[0][0] == ')') {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][length + 1];

        // Start with balance 1 because grid[0][0] is '('
        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                int change = (grid[i][j] == '(') ? 1 : -1;

                for (int balance = 0; balance <= length; balance++) {

                    int newBalance = balance + change;

                    // Balance can never be negative.
                    if (newBalance < 0 || newBalance > length) {
                        continue;
                    }

                    // Come from top
                    if (i > 0 && dp[i - 1][j][balance]) {
                        dp[i][j][newBalance] = true;
                    }

                    // Come from left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // At the end, balance must be exactly 0.
        return dp[m - 1][n - 1][0];
    }
}