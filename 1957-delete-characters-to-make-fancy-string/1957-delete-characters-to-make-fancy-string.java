class Solution {
    public String makeFancyString(String s) {
         StringBuilder result = new StringBuilder();

        // Add the first character
        result.append(s.charAt(0));
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            // Check if current character is same as the previous
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                count = 1; // reset count
            }

            // Only append if count is less than 3
            if (count < 3) {
                result.append(s.charAt(i));
            }
        }

        return result.toString();
    }
}