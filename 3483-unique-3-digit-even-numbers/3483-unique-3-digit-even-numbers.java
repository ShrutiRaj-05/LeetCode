class Solution {
    public int totalNumbers(int[] digits) {
        int ans = 0;
        for(int num=100; num<=999;num++){
            if(num%2!=0){
                continue;
            }
            int[] need = new int[10];
            int temp = num;
            while(temp>0){
                need[temp%10]++;
                temp/=10;
            }
            int[] available = new int[10];
            for(int digit: digits){
                available[digit]++;
            }
            boolean possible = true;
            for(int i=0;i<10;i++){
                if(need[i] > available[i]){
                    possible =  false;
                    break;
                }
            }
            if(possible){
                ans++;
            }
        }
        return ans;
    }
}