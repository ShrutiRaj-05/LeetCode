class Solution {
    public int maximumGain(String s, int x, int y) {
         if (x > y) {
            // Remove "ab" first, then "ba"
            StringBuilder afterAB = new StringBuilder();
            int points1 = removePair(s, 'a', 'b', x, afterAB);
            int points2 = removePair(afterAB.toString(), 'b', 'a', y, null);
            return points1 + points2;
        } else {
            // Remove "ba" first, then "ab"
            StringBuilder afterBA = new StringBuilder();
            int points1 = removePair(s, 'b', 'a', y, afterBA);
            int points2 = removePair(afterBA.toString(), 'a', 'b', x, null);
            return points1 + points2;
        }
    }

    private static int removePair(String s, char first, char second, int score, StringBuilder result) {
        StringBuilder stack = new StringBuilder();
        int points = 0;

        for (char ch : s.toCharArray()) {
            if (stack.length() > 0 && stack.charAt(stack.length() - 1) == first && ch == second) {
                stack.deleteCharAt(stack.length() - 1);
                points += score;
            } else {
                stack.append(ch);
            }
        }

        if (result != null) {
            result.append(stack);
        }

        return points;
    }

    }
