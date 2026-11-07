
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Free servers: weight, index
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> a[0] != b[0]
                ? Integer.compare(a[0], b[0])
                : Integer.compare(a[1], b[1])
        );

        // Busy servers: finishTime, weight, index
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0]
                ? Integer.compare(a[0], b[0])
                : Integer.compare(a[2], b[2])
        );

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {

            // Task j arrives at second j
            time = Math.max(time, j);

            // Release all completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                free.offer(new int[]{s[1], s[2]});
            }

            // If no server is free, wait for the earliest one
            if (free.isEmpty()) {

                time = busy.peek()[0];

                // Release ALL servers finishing at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    int[] s = busy.poll();
                    free.offer(new int[]{s[1], s[2]});
                }
            }

            // Select the best available server
            int[] server = free.poll();

            int weight = server[0];
            int index = server[1];

            ans[j] = index;

            // Assign task and calculate finishing time
            busy.offer(new int[]{
                (int)(time + tasks[j]),
                weight,
                index
            });
        }

        return ans;
    }
}
