class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;

        int[][] litterId = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                litterId[i][j] = -1;

                if (classroom[i].charAt(j) == 'S') {
                    startR = i;
                    startC = j;
                }
            }
        }

        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int allCollected = (1 << litterCount) - 1;

        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        java.util.ArrayDeque<Integer> queue =
            new java.util.ArrayDeque<>();

        int startMask = 0;

        int startState =
            (((startR * n + startC) * (energy + 1) + energy)
            * (1 << litterCount)) + startMask;

        queue.offer(startState);

        visited[startR][startC][energy][startMask] = true;

        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                int state = queue.poll();

                int mask = state % (1 << litterCount);
                state /= (1 << litterCount);

                int currentEnergy = state % (energy + 1);
                state /= (energy + 1);

                int pos = state;

                int r = pos / n;
                int c = pos % n;

                if (mask == allCollected) {
                    return moves;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    if (currentEnergy == 0) {
                        continue;
                    }

                    int newEnergy = currentEnergy - 1;

                    char cell = classroom[nr].charAt(nc);

                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    if (cell == 'L') {
                        int id = litterId[nr][nc];
                        newMask = mask | (1 << id);
                    }

                    if (!visited[nr][nc][newEnergy][newMask]) {

                        visited[nr][nc][newEnergy][newMask] = true;

                        int newState =
                            (((nr * n + nc) * (energy + 1) + newEnergy)
                            * (1 << litterCount)) + newMask;

                        queue.offer(newState);
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}