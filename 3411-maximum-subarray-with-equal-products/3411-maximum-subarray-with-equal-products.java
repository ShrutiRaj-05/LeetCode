
class Solution {

    public int maxLength(int[] nums) {

        int n = nums.length;

        // Any subarray of length 2 is product equivalent.
        int maxLen = 2;

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                // Check whether nums[j] is coprime
                // with every previous element.
                boolean valid = true;

                for (int k = i; k < j; k++) {

                    if (gcd(nums[k], nums[j]) != 1) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }

                // Length of current subarray
                int len = j - i + 1;

                maxLen = Math.max(maxLen, len);
            }
        }

        return maxLen;
    }

    // Euclidean algorithm to calculate GCD
    private int gcd(int a, int b) {

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}
