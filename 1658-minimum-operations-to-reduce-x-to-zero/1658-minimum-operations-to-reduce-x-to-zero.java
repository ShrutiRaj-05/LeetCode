class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;

        // Calculate total sum
        for (int num : nums) {
            total += num;
        }

        int target = total - x;

        // Impossible
        if (target < 0) {
            return -1;
        }

        // Need to remove all elements
        if (target == 0) {
            return nums.length;
        }

        int left = 0;
        int sum = 0;
        int maxLength = -1;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            // Shrink window if sum is too large
            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            // Found a subarray with required sum
            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        // Elements removed = total elements - elements kept
        return nums.length - maxLength;
    }
}