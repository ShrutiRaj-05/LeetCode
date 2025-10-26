class Solution {
    public String toHex(int num) {
        if(num == 0) return "0";
        char[] hexChars = "0123456789abcdef".toCharArray();
        StringBuilder hex = new StringBuilder();
        while(num!=0){
            int lastFourBits = num & 0xf;
            hex.insert(0, hexChars[lastFourBits]);
            num>>>=4;
            
        }
        return hex.toString();
    }
}