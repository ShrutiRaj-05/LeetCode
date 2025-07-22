class Solution {
    public int maximumUniqueSubarray(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        int left = 0, currSum = 0, maxScore = 0;
       for(int right = 0; right<nums.length; right++){
            while(seen.contains(nums[right])){
                    seen.remove(nums[left]);
                    currSum -= nums[left];
                    left++;
            }
            seen.add(nums[right]);
            currSum+= nums[right];
            maxScore = Math.max(maxScore, currSum);
       }
       return maxScore;
    }
}