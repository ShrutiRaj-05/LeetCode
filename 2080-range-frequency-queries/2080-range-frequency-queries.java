import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RangeFreqQuery {

    // Maps each value to a sorted list of indices where it appears in arr
    private final Map<Integer, List<Integer>> valueToIndices;

    public RangeFreqQuery(int[] arr) {
        valueToIndices = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            valueToIndices.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
    }

    public int query(int left, int right, int value) {
        if (!valueToIndices.containsKey(value)) {
            return 0;
        }

        List<Integer> indices = valueToIndices.get(value);

        // Find lower bound: first index >= left
        int start = findLowerBound(indices, left);
        
        // Find upper bound: last index <= right
        int end = findUpperBound(indices, right);

        if (start > end) {
            return 0;
        }

        return end - start + 1;
    }

    // Binary search for the first element >= target
    private int findLowerBound(List<Integer> list, int target) {
        int low = 0, high = list.size() - 1;
        int ans = list.size();

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    // Binary search for the last element <= target
    private int findUpperBound(List<Integer> list, int target) {
        int low = 0, high = list.size() - 1;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) <= target) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }
}

/**
 * Your RangeFreqQuery object will be instantiated and called as such:
 * RangeFreqQuery obj = new RangeFreqQuery(arr);
 * int param_1 = obj.query(left,right,value);
 */