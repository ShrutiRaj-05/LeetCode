class Solution {
    public boolean isPowerOfThree(int n) {
        if (n <= 0) return false;

        // Keep dividing n by 3 if it is divisible
        while (n % 3 == 0) {
            n /= 3;
        }

        // If n becomes 1, it was a power of three
        return n == 1;

    }
}