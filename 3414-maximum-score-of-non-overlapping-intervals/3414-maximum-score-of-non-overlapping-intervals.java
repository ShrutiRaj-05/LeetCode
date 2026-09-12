import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, index;

        Interval(int l, int r, int w, int index) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.index = index;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    // Find first interval whose left boundary is > current right boundary
    private int findNext(Interval[] arr, int i) {

        int target = arr[i].r;

        int left = i + 1;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid].l > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Return true if a is better than b
    private boolean better(State a, State b) {

        // Higher score is better
        if (a.score != b.score) {
            return a.score > b.score;
        }

        // If score is same, lexicographically smaller indices are better
        int n = Math.min(a.indices.size(), b.indices.size());

        for (int i = 0; i < n; i++) {

            if (!a.indices.get(i).equals(b.indices.get(i))) {
                return a.indices.get(i) < b.indices.get(i);
            }
        }

        // If one is a prefix of the other,
        // smaller array is lexicographically smaller
        return a.indices.size() < b.indices.size();
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        Interval[] arr = new Interval[n];

        // Store original index
        for (int i = 0; i < n; i++) {

            arr[i] = new Interval(
                    intervals.get(i).get(0),
                    intervals.get(i).get(1),
                    intervals.get(i).get(2),
                    i
            );
        }

        // Sort by starting position
        Arrays.sort(arr, (a, b) -> {

            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }

            return Integer.compare(a.r, b.r);
        });

        /*
         * dp[i][k] =
         * best answer using intervals from i onward
         * when we can choose at most k intervals.
         */
        State[][] dp = new State[n + 1][5];

        // IMPORTANT:
        // Choosing 0 intervals is always possible.
        for (int i = 0; i <= n; i++) {
            dp[i][0] = new State(
                    0,
                    new ArrayList<>()
            );
        }

        // At i = n, no intervals remain.
        for (int k = 1; k <= 4; k++) {
            dp[n][k] = new State(
                    0,
                    new ArrayList<>()
            );
        }

        // Fill DP from right to left
        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                // Option 1: Skip current interval
                State skip = dp[i + 1][k];

                // Option 2: Take current interval
                int next = findNext(arr, i);

                List<Integer> takeIndices =
                        new ArrayList<>(dp[next][k - 1].indices);

                takeIndices.add(arr[i].index);

                // Sort original indices
                Collections.sort(takeIndices);

                State take = new State(
                        (long) arr[i].w + dp[next][k - 1].score,
                        takeIndices
                );

                // Choose better option
                dp[i][k] = better(take, skip) ? take : skip;
            }
        }

        // Final answer
        List<Integer> answer = dp[0][4].indices;

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }
}