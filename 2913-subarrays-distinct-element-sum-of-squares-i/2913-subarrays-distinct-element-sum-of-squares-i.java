class Solution {
    public int sumCounts(List<Integer> nums) {
        int total = 0;
        int n = nums.size();
        for(int i=0;i<n;i++){
            Set<Integer> distinct = new HashSet<>();
            for(int j=i;j<n;j++){
                distinct.add(nums.get(j));
                int count = distinct.size();
                total += count*count;
            }
        }
        return total;
    }
}