class Solution {
    public int minOperations(String s) {
        boolean isSorted = true;
        char[] cs = s.toCharArray();
        int n = cs.length;
        char mn = cs[0], mx = cs[0];
        
        // Find min, max, and check if the string is already sorted
        for (int i = 1; i < n; ++i) {
            mn = (char) Math.min(mn, cs[i]);
            mx = (char) Math.max(mx, cs[i]);
            if (cs[i] < cs[i - 1]) {
                isSorted = false;
            }
        }
        
        // If already sorted, 0 operations needed
        if (isSorted) {
            return 0;
        }
        
        // For length 2 strings that aren't sorted, it's impossible
        if (n == 2) {
            return -1;
        }
        
        // 1 operation if the minimum is at the beginning or maximum is at the end
        if (cs[0] == mn || cs[n - 1] == mx) {
            return 1;
        }
        
        // 2 operations if the minimum or maximum is anywhere in the interior
        for (int i = 1; i < n - 1; ++i) {
            if (cs[i] == mn || cs[i] == mx) {
                return 2;
            }
        }
        
        // Otherwise, 3 operations are required
        return 3;
    }
}