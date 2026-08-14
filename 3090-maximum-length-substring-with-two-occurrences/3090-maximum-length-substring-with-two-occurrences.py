class Solution:
    def maximumLengthSubstring(self, s: str) -> int:
        left = 0
        max_len = 0
        char_count = {}
        
        for right in range(len(s)):
            char = s[right]
            char_count[char] = char_count.get(char, 0) + 1
            
            # Shrink the window if the frequency of s[right] exceeds 2
            while char_count[char] > 2:
                char_count[s[left]] -= 1
                left += 1
                
            # Update maximum substring length
            max_len = max(max_len, right - left + 1)
            
        return max_len