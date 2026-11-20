import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        HashMap<Integer, Integer> dp = new HashMap<>();
        int maxLength = 0;

        for (int x : nums) {
            // Save the previous DP value before updating
            int oldX = dp.getOrDefault(x, 0);

            // Select x without increasing it
            dp.put(x, Math.max(dp.getOrDefault(x, 0),
                              dp.getOrDefault(x - 1, 0) + 1));

            // Select x after increasing it by 1
            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0),
                                   oldX + 1));

            maxLength = Math.max(maxLength,
                    Math.max(dp.get(x), dp.get(x + 1)));
        }

        return maxLength;
    }
}