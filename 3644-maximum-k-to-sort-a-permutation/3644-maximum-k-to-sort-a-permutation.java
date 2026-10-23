class Solution {
    public int sortPermutation(int[] nums) {
        int ans = -1; // Initialize with all bits set (-1 in two's complement)
        
        for (int i = 0; i < nums.length; i++) {
            // If the element is misplaced, it must participate in a swap
            if (nums[i] != i) {
                ans &= nums[i];
            }
        }
        
        // If no elements were misplaced, ans remains -1, so we return 0.
        // Otherwise, return the computed bitwise AND.
        return Math.max(ans, 0);
    }
}