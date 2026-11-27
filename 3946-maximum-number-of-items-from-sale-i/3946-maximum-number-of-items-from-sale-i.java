
import java.util.*;

class Solution {

    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Step 1: Calculate benefit of each item
        int[] weight = new int[n];

        for (int i = 0; i < n; i++) {

            int freeCount = 0;

            for (int j = 0; j < n; j++) {

                if (i != j &&
                    items[j][0] % items[i][0] == 0) {

                    freeCount++;
                }
            }

            weight[i] = 1 + freeCount;
        }

        // Step 2: Sort items by descending price
        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) ->
            Integer.compare(items[b][1], items[a][1])
        );

        int INF = -1000000000;

        int[] dp = new int[budget + 1];

        Arrays.fill(dp, INF);
        dp[0] = 0;

        int answer = 0;
        int pos = 0;

        // Step 3: Process each distinct price group
        while (pos < n) {

            int price = items[order[pos]][1];

            int[] base = dp.clone();

            int[] withMin = new int[budget + 1];

            Arrays.fill(withMin, INF);

            // Process items with the same price
            while (pos < n &&
                   items[order[pos]][1] == price) {

                int idx = order[pos];
                int w = weight[idx];

                for (int c = budget; c >= price; c--) {

                    // Select first item of this price group
                    if (base[c - price] != INF) {
                        withMin[c] = Math.max(
                            withMin[c],
                            base[c - price] + w
                        );
                    }

                    // Select another item from this group
                    if (withMin[c - price] != INF) {
                        withMin[c] = Math.max(
                            withMin[c],
                            withMin[c - price] + w
                        );
                    }

                    // Normal 0/1 knapsack update
                    if (dp[c - price] != INF) {
                        dp[c] = Math.max(
                            dp[c],
                            dp[c - price] + w
                        );
                    }
                }

                pos++;
            }

            // Step 4: Calculate additional purchases
            for (int c = 0; c <= budget; c++) {

                if (withMin[c] != INF) {

                    int extra = (budget - c) / price;

                    answer = Math.max(
                        answer,
                        withMin[c] + extra
                    );
                }
            }
        }

        return answer;
    }
}
