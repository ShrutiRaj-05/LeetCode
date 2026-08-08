class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();

        // suf[i] = length of the longest suffix of word2 that can be matched
        // exactly (as a subsequence, 0 changes) using word1[i..n-1]
        int[] suf = new int[n + 1];
        suf[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            suf[i] = suf[i + 1];
            if (suf[i] < m && word1.charAt(i) == word2.charAt(m - 1 - suf[i])) {
                suf[i]++;
            }
        }

        int[] result = new int[m];
        int i = 0, j = 0;
        boolean changed = false;

        while (i < n && j < m) {
            if (word1.charAt(i) == word2.charAt(j)) {
                result[j] = i;
                i++; j++;
            } else if (!changed && suf[i + 1] >= m - j - 1) {
                // spend the single allowed change here
                result[j] = i;
                i++; j++;
                changed = true;
            } else {
                i++;
            }
        }

        if (j < m) return new int[0];
        return result;
    }
}