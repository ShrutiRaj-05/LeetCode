class Solution {
    int k;
    int size;
    int[] product;
    int[] count;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        size = 1;
        while (size < nums.length) {
            size *= 2;
        }

        product = new int[2 * size];
        count = new int[2 * size * k];
        for (int i = 0; i < 2 * size; i++) {
            product[i] = 1 % k;
        }

        build(nums);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];
            update(index, value);
            int[] result = query(start, nums.length);

            answer[i] = result[x];
        }

        return answer;
    }

    private void build(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int node = size + i;
            int rem = nums[i] % k;

            product[node] = rem;
            count[node * k + rem] = 1;
        }

        for (int node = size - 1; node > 0; node--) {
            merge(node);
        }
    }

    private void merge(int node) {
        int left = node * 2;
        int right = left + 1;

        product[node] = (product[left] * product[right]) % k;
        for (int r = 0; r < k; r++) {
            count[node * k + r] = count[left * k + r];
        }
        for (int r = 0; r < k; r++) {
            int ways = count[right * k + r];

            if (ways > 0) {
                int newRemainder = (product[left] * r) % k;
                count[node * k + newRemainder] += ways;
            }
        }
    }

    private void update(int index, int value) {
        int node = size + index;
        int rem = value % k;

        product[node] = rem;

        for (int r = 0; r < k; r++) {
            count[node * k + r] = 0;
        }

        count[node * k + rem] = 1;

        node /= 2;

        while (node > 0) {
            merge(node);
            node /= 2;
        }
    }
    private int[] query(int left, int right) {
        int[] leftCount = new int[k];
        int[] rightCount = new int[k];

        int leftProduct = 1 % k;
        int rightProduct = 1 % k;

        left += size;
        right += size;

        while (left < right) {
            if ((left & 1) == 1) {
                int[] newCount = leftCount.clone();

                for (int r = 0; r < k; r++) {
                    int ways = count[left * k + r];

                    if (ways > 0) {
                        int newRemainder = (leftProduct * r) % k;
                        newCount[newRemainder] += ways;
                    }
                }

                leftProduct = (leftProduct * product[left]) % k;
                leftCount = newCount;

                left++;
            }
            if ((right & 1) == 1) {
                --right;

                int[] newCount = new int[k];
                for (int r = 0; r < k; r++) {
                    newCount[r] = count[right * k + r];
                }
                for (int r = 0; r < k; r++) {
                    int ways = rightCount[r];

                    if (ways > 0) {
                        int newRemainder =
                                (product[right] * r) % k;

                        newCount[newRemainder] += ways;
                    }
                }

                rightProduct =
                        (product[right] * rightProduct) % k;

                rightCount = newCount;
            }

            left /= 2;
            right /= 2;
        }
        for (int r = 0; r < k; r++) {
            int ways = rightCount[r];

            if (ways > 0) {
                int newRemainder = (leftProduct * r) % k;
                leftCount[newRemainder] += ways;
            }
        }

        return leftCount;
    }
}