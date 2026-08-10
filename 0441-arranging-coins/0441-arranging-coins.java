class Solution {
    public int arrangeCoins(int n) {
        // Use 8L to prevent integer overflow during 8 * n calculation
        return (int) ((Math.sqrt(1 + 8L * n) - 1) / 2);
    }
}