
class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {

        int[] daysInMonth = {31, 28, 31, 30, 31, 30,
                             31, 31, 30, 31, 30, 31};

        int a1 = convertToDays(arriveAlice, daysInMonth);
        int a2 = convertToDays(leaveAlice, daysInMonth);
        int b1 = convertToDays(arriveBob, daysInMonth);
        int b2 = convertToDays(leaveBob, daysInMonth);

        int start = Math.max(a1, b1);
        int end = Math.min(a2, b2);

        return Math.max(0, end - start + 1);
    }

    private int convertToDays(String date, int[] daysInMonth) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));

        int totalDays = day;

        for (int i = 0; i < month - 1; i++) {
            totalDays += daysInMonth[i];
        }

        return totalDays;
    }
}
