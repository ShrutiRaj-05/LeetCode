import java.util.Arrays;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = Integer.MAX_VALUE;

        int[] best = new int[n];
        Arrays.fill(best, INF);

        int left = 0;
        long sum = 0;

        int minLength = INF;
        int answer = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int currentLength = right - left + 1;

                // Find a previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        currentLength + best[left - 1]
                    );
                }

                minLength = Math.min(minLength, currentLength);
            }

            // Store the shortest valid subarray found so far
            best[right] = minLength;
        }

        return answer == INF ? -1 : answer;
    }
}