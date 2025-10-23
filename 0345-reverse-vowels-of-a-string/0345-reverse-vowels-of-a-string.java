class Solution {
    public static String reverseVowels(String s) {   // <-- added static
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        String vowels = "aeiouAEIOU";

        while (left < right) {
            while (left < right && vowels.indexOf(chars[left]) == -1) left++;
            while (left < right && vowels.indexOf(chars[right]) == -1) right--;

            if (left < right) {
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String s1 = "IceCreAm";
        String s2 = "leetcode";

        System.out.println("Input: " + s1);
        System.out.println("Output: " + reverseVowels(s1));

        System.out.println("\nInput: " + s2);
        System.out.println("Output: " + reverseVowels(s2));
    }
}
