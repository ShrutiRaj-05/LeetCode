import java.util.Arrays;

class Solution {

    private static class Factors {
        int c2, c3, c5, c7;

        Factors(int c2, int c3, int c5, int c7) {
            this.c2 = c2;
            this.c3 = c3;
            this.c5 = c5;
            this.c7 = c7;
        }

        Factors() {
            this(0, 0, 0, 0);
        }
    }

    private Factors getFactors(long t) {
        Factors f = new Factors();
        while (t % 2 == 0) { f.c2++; t /= 2; }
        while (t % 3 == 0) { f.c3++; t /= 3; }
        while (t % 5 == 0) { f.c5++; t /= 5; }
        while (t % 7 == 0) { f.c7++; t /= 7; }
        
        if (t > 1) return new Factors(-1, -1, -1, -1);
        return f;
    }

    private Factors digitFactors(int d) {
        switch (d) {
            case 2: return new Factors(1, 0, 0, 0);
            case 3: return new Factors(0, 1, 0, 0);
            case 4: return new Factors(2, 0, 0, 0);
            case 5: return new Factors(0, 0, 1, 0);
            case 6: return new Factors(1, 1, 0, 0);
            case 7: return new Factors(0, 0, 0, 1);
            case 8: return new Factors(3, 0, 0, 0);
            case 9: return new Factors(0, 2, 0, 0);
            default: return new Factors(0, 0, 0, 0);
        }
    }

    private int minDigitsNeeded(Factors req) {
        int c2 = Math.max(0, req.c2);
        int c3 = Math.max(0, req.c3);
        int c5 = Math.max(0, req.c5);
        int c7 = Math.max(0, req.c7);

        int count8 = c2 / 3;
        c2 %= 3;
        int count9 = c3 / 2;
        c3 %= 2;

        int count4 = 0, count2 = 0, count6 = 0, count3 = 0;
        if (c2 == 2) {
            count4 = 1;
            c2 = 0;
        }
        if (c2 == 1 && c3 == 1) {
            count6 = 1;
            c2 = 0;
            c3 = 0;
        }
        if (c2 == 1) count2 = 1;
        if (c3 == 1) count3 = 1;

        return count8 + count9 + count4 + count6 + count2 + count3 + c5 + c7;
    }

    public String smallestNumber(String num, long t) {
        Factors req = getFactors(t);
        if (req.c2 == -1) return "-1"; // Impossible prime factor

        int n = num.length();

        // Precalculate running factors for exact prefix of num
        Factors[] pref = new Factors[n + 1];
        pref[0] = new Factors();
        int firstZero = -1;

        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                firstZero = i;
                break;
            }
            Factors f = digitFactors(num.charAt(i) - '0');
            pref[i + 1] = new Factors(
                pref[i].c2 + f.c2,
                pref[i].c3 + f.c3,
                pref[i].c5 + f.c5,
                pref[i].c7 + f.c7
            );
        }

        // Check if num itself (if zero-free) meets the requirement
        if (firstZero == -1) {
            Factors rem = new Factors(
                req.c2 - pref[n].c2,
                req.c3 - pref[n].c3,
                req.c5 - pref[n].c5,
                req.c7 - pref[n].c7
            );
            if (minDigitsNeeded(rem) <= 0) return num;
        }

        int maxPrefix = (firstZero == -1) ? n - 1 : firstZero;

        // Try prefix match of length `i` from right to left
        for (int i = maxPrefix; i >= 0; i--) {
            int startDigit = num.charAt(i) - '0' + 1;
            for (int d = startDigit; d <= 9; d++) {
                Factors fD = digitFactors(d);
                Factors rem = new Factors(
                    req.c2 - pref[i].c2 - fD.c2,
                    req.c3 - pref[i].c3 - fD.c3,
                    req.c5 - pref[i].c5 - fD.c5,
                    req.c7 - pref[i].c7 - fD.c7
                );

                int remLen = n - 1 - i;
                if (minDigitsNeeded(rem) <= remLen) {
                    StringBuilder res = new StringBuilder();
                    res.append(num, 0, i).append(d);

                    for (int j = i + 1; j < n; j++) {
                        for (int nextD = 1; nextD <= 9; nextD++) {
                            Factors fNext = digitFactors(nextD);
                            Factors nextRem = new Factors(
                                rem.c2 - fNext.c2,
                                rem.c3 - fNext.c3,
                                rem.c5 - fNext.c5,
                                rem.c7 - fNext.c7
                            );
                            if (minDigitsNeeded(nextRem) <= n - 1 - j) {
                                res.append(nextD);
                                rem = nextRem;
                                break;
                            }
                        }
                    }
                    return res.toString();
                }
            }
        }

        // FIXED STEP 3: Handle arbitrary target length >= n + 1
        int targetLen = Math.max(n + 1, minDigitsNeeded(req));
        
        StringBuilder res = new StringBuilder();
        Factors rem = new Factors(req.c2, req.c3, req.c5, req.c7);

        for (int j = 0; j < targetLen; j++) {
            for (int d = 1; d <= 9; d++) {
                Factors fD = digitFactors(d);
                Factors nextRem = new Factors(
                    rem.c2 - fD.c2,
                    rem.c3 - fD.c3,
                    rem.c5 - fD.c5,
                    rem.c7 - fD.c7
                );
                if (minDigitsNeeded(nextRem) <= targetLen - 1 - j) {
                    res.append(d);
                    rem = nextRem;
                    break;
                }
            }
        }

        return res.toString();
    }
}