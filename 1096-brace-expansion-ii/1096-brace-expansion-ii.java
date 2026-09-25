import java.util.*;

class Solution {
    int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parseUnion(expression);

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    // Handles union: e1,e2,e3
    private Set<String> parseUnion(String s) {
        Set<String> result = new HashSet<>();

        while (true) {
            result.addAll(parseConcat(s));

            if (index < s.length() && s.charAt(index) == ',') {
                index++; // skip comma
            } else {
                break;
            }
        }

        return result;
    }

    // Handles concatenation: e1e2e3
    private Set<String> parseConcat(String s) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()
                && s.charAt(index) != '}'
                && s.charAt(index) != ',') {

            Set<String> part = parseFactor(s);

            result = concatenate(result, part);
        }

        return result;
    }

    // Handles one letter or one {...} group
    private Set<String> parseFactor(String s) {
        Set<String> result = new HashSet<>();

        if (s.charAt(index) == '{') {
            index++; // skip {

            result = parseUnion(s);

            index++; // skip }
        } else {
            result.add(String.valueOf(s.charAt(index)));
            index++;
        }

        return result;
    }

    // Cartesian product of two sets
    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}