class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        // Count characters in s
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check whether palindrome is possible
        int oddCount = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                oddCount++;
                middle = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        // Number of characters in left half
        int halfLen = n / 2;

        // Frequency of characters in left half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        // Build the smallest possible half
        StringBuilder smallestHalf = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            while (halfFreq[i] > 0) {
                smallestHalf.append((char) ('a' + i));
                halfFreq[i]--;
            }
        }

        String half = smallestHalf.toString();

        // 1. Check the smallest palindrome
        String smallestPalindrome = makePalindrome(half, middle);

        if (smallestPalindrome.compareTo(target) > 0) {
            return smallestPalindrome;
        }

        // Target's first half
        String targetHalf = target.substring(0, halfLen);

        // 2. Try using target's first half exactly
        if (canBuildHalf(targetHalf, freq)) {

            String candidate = makePalindrome(targetHalf, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // 3. Find the smallest half strictly greater than targetHalf
        String nextHalf = findNextGreaterHalf(targetHalf, freq);

        if (nextHalf == null) {
            return "";
        }

        return makePalindrome(nextHalf, middle);
    }

    private boolean canBuildHalf(String half, int[] freq) {

        int[] count = new int[26];

        for (int i = 0; i < 26; i++) {
            count[i] = freq[i] / 2;
        }

        for (char ch : half.toCharArray()) {
            int index = ch - 'a';

            if (count[index] == 0) {
                return false;
            }

            count[index]--;
        }

        return true;
    }

    private String findNextGreaterHalf(String targetHalf, int[] freq) {

        int len = targetHalf.length();

        int[] original = new int[26];

        for (int i = 0; i < 26; i++) {
            original[i] = freq[i] / 2;
        }

        // Try changing from right to left
        for (int i = len - 1; i >= 0; i--) {

            int[] remaining = original.clone();

            // Build prefix equal to targetHalf
            boolean possible = true;

            for (int j = 0; j < i; j++) {

                int index = targetHalf.charAt(j) - 'a';

                if (remaining[index] == 0) {
                    possible = false;
                    break;
                }

                remaining[index]--;
            }

            if (!possible) {
                continue;
            }

            int current = targetHalf.charAt(i) - 'a';

            // Choose smallest character greater than current
            for (int c = current + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    StringBuilder result = new StringBuilder();

                    // Same prefix
                    for (int j = 0; j < i; j++) {
                        result.append(targetHalf.charAt(j));
                    }

                    // Greater character
                    result.append((char) ('a' + c));
                    remaining[c]--;

                    // Smallest suffix
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            result.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return result.toString();
                }
            }
        }

        return null;
    }

    private String makePalindrome(String half, char middle) {

        StringBuilder result = new StringBuilder();

        // Left half
        result.append(half);

        // Middle for odd length
        if (middle != 0) {
            result.append(middle);
        }

        // Right half
        for (int i = half.length() - 1; i >= 0; i--) {
            result.append(half.charAt(i));
        }

        return result.toString();
    }
}