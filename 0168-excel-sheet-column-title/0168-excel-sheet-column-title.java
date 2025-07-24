class Solution {
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        while(columnNumber>0){
            columnNumber--;
            int remainder = columnNumber%26;
            char c = (char)('A'+ remainder);
            sb.append(c);
            columnNumber = columnNumber/26;
        }
        return sb.reverse().toString();
    }
}