
import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;
    long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Precompute combinations using Pascal's Triangle
        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = 1;
            comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] =
                    (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        List<Integer> arr = new ArrayList<>();
        for (int num : nums) {
            arr.add(num);
        }

        // Subtract 1 because the original ordering is also counted
        return (int) ((countWays(arr) - 1 + MOD) % MOD);
    }

    private long countWays(List<Integer> nums) {
        int n = nums.size();

        // Base case: at most two nodes have only one valid ordering
        if (n <= 2) {
            return 1;
        }

        int root = nums.get(0);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        // Divide elements according to the BST root
        for (int i = 1; i < n; i++) {
            if (nums.get(i) < root) {
                left.add(nums.get(i));
            } else {
                right.add(nums.get(i));
            }
        }

        // Choose positions for the left subtree elements
        long waysToArrange =
            comb[n - 1][left.size()];

        // Multiply by the valid arrangements of both subtrees
        long leftWays = countWays(left);
        long rightWays = countWays(right);

        return (((waysToArrange * leftWays) % MOD)
                         * rightWays) % MOD;
    }
}
