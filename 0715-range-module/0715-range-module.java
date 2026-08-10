import java.util.Map;
import java.util.TreeMap;

class RangeModule {

    // Maps interval start (left) -> interval end (right)
    private final TreeMap<Integer, Integer> map;

    public RangeModule() {
        map = new TreeMap<>();
    }

    public void addRange(int left, int right) {
        // Look up floor keys that could overlap with 'left' and 'right'
        Integer l = map.floorKey(left);
        Integer r = map.floorKey(right);

        // Merge with existing interval on the left if overlapping/adjacent
        if (l != null && map.get(l) >= left) {
            left = l;
        }

        // Merge with existing interval on the right if it extends beyond 'right'
        if (r != null && map.get(r) > right) {
            right = map.get(r);
        }

        // Delete all fully covered sub-intervals in between
        map.subMap(left, true, right, true).clear();

        // Store merged interval
        map.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        // Find floor interval starting at or before 'left'
        Integer l = map.floorKey(left);

        // Entire [left, right) must be covered by this single interval
        return l != null && map.get(l) >= right;
    }

    public void removeRange(int left, int right) {
        Integer l = map.floorKey(left);
        Integer r = map.floorKey(right);

        // Preserve right fragment [right, r_end) if 'right' cuts through an interval
        if (r != null && map.get(r) > right) {
            map.put(right, map.get(r));
        }

        // Preserve left fragment [l_start, left) if 'left' cuts through an interval
        if (l != null && map.get(l) > left) {
            map.put(l, left);
        }

        // Remove all fully enclosed sub-intervals in [left, right)
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