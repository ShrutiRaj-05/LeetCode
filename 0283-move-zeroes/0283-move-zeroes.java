class Solution {
    public void moveZeroes(int[] nums) {
        int nonZeroIndex = 0;
        for(int i=0; i<nums.length;i++){
            if(nums[i]!=0){
                nums[nonZeroIndex] = nums[i];
                nonZeroIndex++;
            }
        }
        while(nonZeroIndex<nums.length){
            nums[nonZeroIndex] = 0;
            nonZeroIndex++;
        }
    }
}