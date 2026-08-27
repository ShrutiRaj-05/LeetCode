class Solution {
    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        // Count characters in s
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Try changing target from right to left
        for (int i = n - 1; i >= 0; i--) {

            int[] remaining = freq.clone();
            boolean possible = true;

            // Check whether target prefix can be formed
            for (int j = 0; j < i; j++) {
                int index = target.charAt(j) - 'a';

                if (remaining[index] == 0) {
                    possible = false;
                    break;
                }

                remaining[index]--;
            }

            if (!possible) {
                continue;
            }

            // Find smallest character greater than target[i]
            int current = target.charAt(i) - 'a';

            for (int c = current + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Add prefix
                    for (int j = 0; j < i; j++) {
                        ans.append(target.charAt(j));
                    }

                    // Add greater character
                    ans.append((char) ('a' + c));
                    remaining[c]--;

                    // Add remaining characters in sorted order
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            ans.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}