class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        
        int m = board.length;
        int n = board[0].length;
        
        // Step 1: Traverse the boundary rows (first and last row)
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs(board, i, 0);
            if (board[i][n - 1] == 'O') dfs(board, i, n - 1);
        }
        
        // Traverse the boundary columns (first and last column)
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfs(board, 0, j);
            if (board[m - 1][j] == 'O') dfs(board, m - 1, j);
        }
        
        // Step 2: Post-processing grid traversal
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X'; // Captured!
                } else if (board[i][j] == 'E') {
                    board[i][j] = 'O'; // Safe, restore back to 'O'
                }
            }
        }
    }
    
    private void dfs(char[][] board, int r, int c) {
        int m = board.length;
        int n = board[0].length;
        
        // Base case: Out of bounds or current cell is not 'O'
        if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') {
            return;
        }
        
        // Mark as 'E' (Edge-connected / Safe)
        board[r][c] = 'E';
        
        // Explore 4-directional neighbors
        dfs(board, r + 1, c);
        dfs(board, r - 1, c);
        dfs(board, r, c + 1);
        dfs(board, r, c - 1);
    }
}