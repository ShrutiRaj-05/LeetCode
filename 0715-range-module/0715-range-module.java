import java.util.Map;
import java.util.TreeMap;

class RangeModule {

    // Maps interval start (left) -> interval end (right)
    private final TreeMap<Integer, Integer> map;

    public RangeModule() {
        map = new TreeMap<>();
    }

    public void addRange(int left, int right) {
        // Find the floor interval (might overlap with 'left')
        Integer l = map.floorKey(left);
        // Find the higher interval (might overlap with 'right')
        Integer r = map.floorKey(right);

        // Adjust left bound if the floor interval overlaps
        if (l != null && map.get(l) >= left) {
            left = l;
        }

        // Adjust right bound if the floor-of-right interval extends past 'right'
        if (r != null && map.get(r) > right) {
            right = map.get(r);
        }

        // Remove all sub-intervals in range [left, right] that will be merged
        map.subMap(left, true, right, true).clear();

        // Insert the newly merged interval
        map.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        // Find the interval that starts at or before 'left'
        Integer l = map.floorKey(left);
        
        // Check if this interval exists and completely covers [left, right)
        return l != null && map.get(l) >= right;
    }

    public void removeRange(int left, int right) {
        Integer l = map.floorKey(left);
        Integer r = map.floorKey(right);

        // If floor of 'right' extends beyond 'right', split it to preserve [right, r_end)
        if (r != null && map.get(r) > right) {
            map.put(right, map.get(r));
        }

        // If floor of 'left' starts before 'left' and ends after 'left', split it to preserve [l_start, left)
        if (l != null && map.get(l) > left) {
            map.put(l, left);
        }

        // Clear all fully overlapped intervals in between
        map.subMap(left, true, right, false).clear();
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */