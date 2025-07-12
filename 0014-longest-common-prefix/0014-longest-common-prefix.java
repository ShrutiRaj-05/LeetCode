class Solution {
    public String longestCommonPrefix(String[] strs) {
         if (strs == null || strs.length == 0) return "";

        String prefix = strs[0]; // Start with the first word as prefix

        for (int i = 1; i < strs.length; i++) {
            // Keep shortening the prefix until it matches the start of strs[i]
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);

                // If prefix becomes empty, no common prefix
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
}