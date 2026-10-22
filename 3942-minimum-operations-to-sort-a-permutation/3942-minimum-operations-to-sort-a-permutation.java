class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = -1;
        
        // Find the index of 0
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }
        
        int ans = Integer.MAX_VALUE;
        
        // Check step = 1 (increasing order to the right)
        if (isValid(nums, n, zero, 1)) {
            ans = Math.min(ans, zero);             // Rotate directly
            ans = Math.min(ans, n - zero + 2);     // Reverse, then rotate, then reverse back equivalents
        }
        
        // Check step = -1 (increasing order to the left / descending rotation)
        if (isValid(nums, n, zero, -1)) {
            ans = Math.min(ans, zero + 2);         // Rotate then reverse
            ans = Math.min(ans, n - zero);         // Reverse then rotate
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    
    private boolean isValid(int[] nums, int n, int zero, int step) {
        for (int i = 1; i < n; i++) {
            int prevIdx = (zero + (i - 1) * step) % n;
            if (prevIdx < 0) prevIdx += n;
            
            int currIdx = (zero + i * step) % n;
            if (currIdx < 0) currIdx += n;
            
            if (nums[prevIdx] > nums[currIdx]) {
                return false;
            }
        }
        return true;
    }
}