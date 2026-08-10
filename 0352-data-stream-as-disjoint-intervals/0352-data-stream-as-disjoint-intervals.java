import java.util.TreeMap;

class SummaryRanges {

    private TreeMap<Integer, int[]> map;

    public SummaryRanges() {
        map = new TreeMap<>();
    }
    
    public void addNum(int value) {
        if (map.containsKey(value)) {
            return;
        }

        Integer lowerKey = map.lowerKey(value);
        Integer higherKey = map.higherKey(value);

        // Case 1: Merge lower and higher intervals (e.g., [1, 2] and [4, 5] merged by 3 -> [1, 5])
        if (lowerKey != null && higherKey != null 
            && map.get(lowerKey)[1] + 1 == value 
            && higherKey == value + 1) {
            
            map.get(lowerKey)[1] = map.get(higherKey)[1];
            map.remove(higherKey);
        } 
        // Case 2: Extend lower interval (e.g., [1, 2] extended by 3 -> [1, 3])
        else if (lowerKey != null && map.get(lowerKey)[1] + 1 >= value) {
            map.get(lowerKey)[1] = Math.max(map.get(lowerKey)[1], value);
        } 
        // Case 3: Extend higher interval (e.g., [4, 5] extended by 3 -> [3, 5])
        else if (higherKey != null && higherKey == value + 1) {
            map.put(value, new int[]{value, map.get(higherKey)[1]});
            map.remove(higherKey);
        } 
        // Case 4: Create a new isolated interval [value, value]
        else {
            map.put(value, new int[]{value, value});
        }
    }
    
    public int[][] getIntervals() {
        return map.values().toArray(new int[map.size()][]);
    }
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(value);
 * int[][] param_2 = obj.getIntervals();
 */