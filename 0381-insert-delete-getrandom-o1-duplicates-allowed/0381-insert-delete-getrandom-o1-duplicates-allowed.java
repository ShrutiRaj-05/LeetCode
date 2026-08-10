import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class RandomizedCollection {

    private final List<Integer> list;
    private final Map<Integer, Set<Integer>> map;
    private final Random rand;

    public RandomizedCollection() {
        list = new ArrayList<>();
        map = new HashMap<>();
        rand = new Random();
    }

    public boolean insert(int val) {
        boolean notPresent = !map.containsKey(val) || map.get(val).isEmpty();

        map.computeIfAbsent(val, k -> new LinkedHashSet<>()).add(list.size());
        list.add(val);

        return notPresent;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val) || map.get(val).isEmpty()) {
            return false;
        }

        // Get an arbitrary index of 'val' to remove
        Set<Integer> valIndices = map.get(val);
        int removeIdx = valIndices.iterator().next();
        valIndices.remove(removeIdx);

        int lastIdx = list.size() - 1;
        int lastVal = list.get(lastIdx);

        // Swap target element with the last element if it's not already the last element
        if (removeIdx != lastIdx) {
            list.set(removeIdx, lastVal);

            // Update indices for lastVal
            Set<Integer> lastValIndices = map.get(lastVal);
            lastValIndices.remove(lastIdx);
            lastValIndices.add(removeIdx);
        }

        // Remove the last element from list
        list.remove(lastIdx);

        return true;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */