class Solution {
    public int maxPalindromes(String s, int k) {

        int n = s.length();

        boolean[][] palindrome = new boolean[n][n];

        // Find all palindromic substrings
        for (int end = 0; end < n; end++) {

            for (int start = end; start >= 0; start--) {

                if (s.charAt(start) == s.charAt(end)
                        && (end - start <= 1 || palindrome[start + 1][end - 1])) {

                    palindrome[start][end] = true;
                }
            }
        }

        // dp[i] = maximum number of palindromes
        // using first i characters
        int[] dp = new int[n + 1];

        for (int end = 0; end < n; end++) {

            // Don't select a palindrome ending here
            dp[end + 1] = dp[end];

            for (int start = 0; start <= end; start++) {

                if (end - start + 1 >= k
                        && palindrome[start][end]) {

                    dp[end + 1] = Math.max(
                        dp[end + 1],
                        dp[start] + 1
                    );
                }
            }
        }

        return dp[n];
    }
}