class Solution {
    public int countSegments(String s) {
        if(s==null) return 0;
        s=s.trim();
        if(s.isEmpty()) return 0;
        return s.split("\\s+").length;
    }
}