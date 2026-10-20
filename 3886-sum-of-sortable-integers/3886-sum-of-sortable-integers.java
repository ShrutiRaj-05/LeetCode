class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;
            int prevMax = Integer.MIN_VALUE;

            for (int start = 0; start < n; start += k) {
                int end = start + k;
                int descents = 0;
                int descentIndex = -1;

                for (int i = start; i < end - 1; i++) {
                    if (nums[i] > nums[i + 1]) {
                        descents++;
                        descentIndex = i;
                    }
                }

                if (nums[end - 1] > nums[start]) {
                    descents++;
                    descentIndex = end - 1;
                }

                if (descents > 1) {
                    valid = false;
                    break;
                }

                int first;
                int last;

                if (descents == 0) {
                    first = nums[start];
                    last = nums[end - 1];
                } else {
                    first = nums[descentIndex + 1 == end ? start : descentIndex + 1];
                    last = nums[descentIndex];
                }

                if (first < prevMax) {
                    valid = false;
                    break;
                }

                prevMax = last;
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}