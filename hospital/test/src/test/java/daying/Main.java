package daying;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length=scanner.nextInt();
        String s = scanner.next();
        Integer res=Integer.MAX_VALUE;
        for (int i = 1; i < length; i++) {
            if (length%i==0){
                Integer j=length/i;
                char[][] data=new char[i][j];
                for(int k=0;k<i;k++){
                    for(int m=0;m<j;m++){
                        data[k][m]=s.charAt(k*data.length+j);
                    }
                }
                int island=numIslands(data,s);
                res=Math.min(res,island);
            }
        }

        System.out.println(res);

    }


    public static int numIslands(char[][] grid,String s) {
        int m=grid.length;
        int n=grid[0].length;
        int res=0;
        boolean[][] visited = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(!visited[i][j]){
                    backTracing(grid,i,j,s.charAt(i* grid.length+j),visited);
                    res++;
                }
            }
        }
        return res;
    }

    public static void backTracing(char[][] grid, int row, int clumn,char ch,boolean[][] visited){
        int m=grid.length;
        int n=grid[0].length;
        if(row<0 || row>=m||clumn<0 || clumn>=n|| grid[row][clumn]!=ch){
            return;
        }



        backTracing(grid,row-1,clumn,ch,visited);
        backTracing(grid,row,clumn-1,ch,visited);
        backTracing(grid,row,clumn+1,ch,visited);
        backTracing(grid,row+1,clumn,ch,visited);


    }
}