
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        int m = queries.length;

        // Step 1: Sort logs according to time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Step 2: Store query indices
        Integer[] indices = new Integer[m];

        for (int i = 0; i < m; i++) {
            indices[i] = i;
        }

        // Step 3: Sort queries using their indices
        Arrays.sort(indices, (a, b) ->
            Integer.compare(queries[a], queries[b])
        );

        int[] ans = new int[m];

        // Frequency of requests for each server
        int[] freq = new int[n + 1];

        int left = 0;
        int right = 0;

        // Number of active servers
        int active = 0;

        // Step 4: Process queries in sorted order
        for (int idx : indices) {

            int q = queries[idx];

            int start = q - x;

            // Add logs whose time is <= query time
            while (right < logs.length &&
                   logs[right][1] <= q) {

                int server = logs[right][0];

                if (freq[server] == 0) {
                    active++;
                }

                freq[server]++;

                right++;
            }

            // Remove logs whose time is < start
            while (left < right &&
                   logs[left][1] < start) {

                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    active--;
                }

                left++;
            }

            // Count servers with zero requests
            ans[idx] = n - active;
        }

        return ans;
    }
}
