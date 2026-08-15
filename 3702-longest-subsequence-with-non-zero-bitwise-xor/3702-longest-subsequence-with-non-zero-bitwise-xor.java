class Solution {
    public int longestSubsequence(int[] nums) {
        int n = nums.length;
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // All elements are zero
        if (!hasNonZero) {
            return 0;
        }

        // XOR of all elements is non-zero
        if (xor != 0) {
            return n;
        }

        // XOR of all elements is zero, remove one non-zero element
        return n - 1;
    }
}