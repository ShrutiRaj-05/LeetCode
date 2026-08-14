import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        // Step 1: Mark visited numbers by negating the values at their corresponding indices
        for (int i = 0; i < nums.length; i++) {
            int targetIndex = Math.abs(nums[i]) - 1;
            
            // Only negate if it's currently positive to avoid flipping back to positive
            if (nums[targetIndex] > 0) {
                nums[targetIndex] = -nums[targetIndex];
            }
        }
        
        // Step 2: Indices that remain positive represent missing numbers
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }
        
        return result;
    }
}