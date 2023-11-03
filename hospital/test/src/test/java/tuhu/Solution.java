package tuhu;

import java.util.*;


public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 给出一个仓库库位二维分布平面图，计算从仓库的最左上角开始拣货，到最右下角拣货完成的成本最少并返回。
     * @param grid int整型二维数组 表示仓库库位二维分布平面图。其中，grid[i][j] 表示在地点 (i, j) 的成本
     * @return int整型
     *
     */
    public static void main(String[] args) {
        int[][] m=new int[3][3];
        m[0][0]=1;
        m[0][1]=3;
        m[0][2]=1;
        m[1][0]=1;
        m[1][1]=5;
        m[1][2]=1;
        m[2][0]=4;
        m[2][1]=2;
        m[2][2]=1;
        minPathSum(m);
    }
    public static int minPathSum (int[][] grid) {
        // write code here
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp=new int[m][n];
        dp[0][0]=grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0]=dp[i-1][0]+grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i]=dp[0][i-1]+grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
}