class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;
        int previous = -1;
        int minDistance = Integer.MAX_VALUE;

        int position = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {

            // Check critical point
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {

                // First critical point
                if (first == -1) {
                    first = position;
                }

                // If a previous critical point exists
                if (previous != -1) {
                    minDistance = Math.min(
                        minDistance,
                        position - previous
                    );
                }

                // Current critical point becomes previous
                previous = position;
            }

            prev = curr;
            curr = curr.next;
            position++;
        }

        // Less than two critical points
        if (first == -1 || first == previous) {
            return new int[]{-1, -1};
        }

        int maxDistance = previous - first;

        return new int[]{minDistance, maxDistance};
    }
}