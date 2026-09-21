class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] answer = new long[k];
        long[] prev = new long[k];
        for(int num: nums){
            int value = num % k;
            long[] cur = new long[k];
            cur[value]++;
            for(int r=0;r<k;r++){
                if(prev[r]>0){
                    int newRemainder = (r*value) % k;
                    cur[newRemainder] += prev[r];
                }
            }
            for(int r=0;r<k;r++){
                answer[r] += cur[r];
            }
            prev = cur;
        }
        return answer;
    }
}