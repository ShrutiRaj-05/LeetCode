class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        
        // If lengths don't match, pattern can't be followed
        if (pattern.length() != words.length) return false;

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];

            // Check if mapping exists for c
            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(w)) {
                    return false; // c maps to a different word
                }
            } else {
                // Check if word already mapped to a different char
                if (wordToChar.containsKey(w)) {
                    return false; // word already mapped to another char
                }
                // Create new mapping
                charToWord.put(c, w);
                wordToChar.put(w, c);
            }
        }

        return true;
    }
}