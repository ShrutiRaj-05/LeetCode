import java.util.ArrayList;
import java.util.List;

class StreamChecker {

    // Trie Node definition
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;
    }

    private final TrieNode root;
    private final List<Character> stream;
    private final int maxWordLen; // Optimization: limit search depth

    public StreamChecker(String[] words) {
        root = new TrieNode();
        stream = new ArrayList<>();
        int maxLen = 0;

        // Insert every word into the Trie in REVERSE order
        for (String word : words) {
            maxLen = Math.max(maxLen, word.length());
            TrieNode curr = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                char ch = word.charAt(i);
                int idx = ch - 'a';
                if (curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode();
                }
                curr = curr.children[idx];
            }
            curr.isWord = true;
        }

        this.maxWordLen = maxLen;
    }

    public boolean query(char letter) {
        stream.add(letter);
        TrieNode curr = root;

        // Traverse backwards from the most recent character
        int streamSize = stream.size();
        int searchLimit = Math.min(streamSize, maxWordLen);

        for (int i = 0; i < searchLimit; i++) {
            char ch = stream.get(streamSize - 1 - i);
            int idx = ch - 'a';

            if (curr.children[idx] == null) {
                return false; // Path breaks, no matching suffix
            }

            curr = curr.children[idx];

            if (curr.isWord) {
                return true; // Found a matching reversed word in Trie!
            }
        }

        return false;
    }
}

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker obj = new StreamChecker(words);
 * boolean param_1 = obj.query(letter);
 */