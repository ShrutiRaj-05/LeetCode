class Solution {
    public long minimumDifference(int[] nums) {
        int n = nums.length / 3;
        int len = nums.length;
        
        long[] prefix = new long[n + 1];
        long[] suffix = new long[n + 1];

        // Use max heap for prefix (we want to keep the smallest sum)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        long prefixSum = 0;
        for (int i = 0; i < 2 * n; i++) {
            prefixSum += nums[i];
            maxHeap.add(nums[i]);

            if (maxHeap.size() > n) {
                prefixSum -= maxHeap.poll();
            }

            if (maxHeap.size() == n) {
                prefix[i - n + 1] = prefixSum;
            }
        }

        // Use min heap for suffix (we want to keep the largest sum)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        long suffixSum = 0;
        for (int i = len - 1; i >= n; i--) {
            suffixSum += nums[i];
            minHeap.add(nums[i]);

            if (minHeap.size() > n) {
                suffixSum -= minHeap.poll();
            }

            if (minHeap.size() == n) {
                suffix[i - n] = suffixSum;
            }
        }

        // Now try all positions and calculate min difference
        long minDiff = Long.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            minDiff = Math.min(minDiff, prefix[i] - suffix[i]);
        }

        return minDiff;
    }

    }
