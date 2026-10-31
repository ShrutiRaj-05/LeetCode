class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1000000007;
        long[][] dp = new long[m][n];
        dp[0][0] = 1;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(dp[i][j]==0){
                    continue;
                }
                    int x = i;
                    int y = j+1;
                    boolean down = false;
                    while(x<m && y<n && grid[x][y]==1){
                        if(down){
                            y++;
                        }else{
                            x++;
                        }
                        down = ! down;
                    }
                    if(x<m && y<n){
                        dp[x][y] = (dp[x][y] + dp[i][j])%MOD;
                    }
                    x = i+1;
                    y = j;
                    down = true;
                    while(x<m && y<n && grid[x][y]==1){
                        if(down){
                            y++;
                        }else{
                            x++;
                        }
                        down = ! down;
                    }
                    if(x<m && y<n){
                        dp[x][y] = (dp[x][y] + dp[i][j])%MOD;
                    }
                }
            }
        return (int) dp[m-1][n-1];
    }
}