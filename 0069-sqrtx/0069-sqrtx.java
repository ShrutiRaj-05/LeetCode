class Solution {
    public int mySqrt(int x) {
        if(x<2){
            return x;    // square root of 0 and 1 is itself
        }
        int left = 1;
        int right = x/2;
        int ans = 0;
        while(left<=right){
            int mid = left + (right-left)/2;
            long square =(long) mid * mid;
            if(square==x){
                return mid;         //exact value
            }
            else if(square<x){
                ans = mid;
                left = mid+1;
            }
            else{
                right = mid-1;
            }
        }
        return ans;
    }
}