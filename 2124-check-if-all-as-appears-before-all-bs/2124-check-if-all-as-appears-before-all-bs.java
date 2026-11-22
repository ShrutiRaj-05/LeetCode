class Solution {
    public boolean checkString(String s) {
        boolean seenB = false;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == 'b') {
                seenB = true;
            } 
            else if (ch == 'a' && seenB) {
                return false;
            }
        }

        return true;
    }
}