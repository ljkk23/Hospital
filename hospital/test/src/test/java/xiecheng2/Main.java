package xiecheng2;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String num = in.nextLine();
        int k=in.nextInt();
        int count=calMod(num,k);
        System.out.println(count);
    }
    public static int calMod(String num,int k){
        int n=num.length();
        int[][] dp=new int[n+1][k+1];
        dp[0][0]=1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <=k; j++) {
                dp[i][j]=dp[i-1][j];
                if (j>0){
                    dp[i][j]=(dp[i][j]+dp[i-1][j-1])%1000000007;
                }
                int digit=num.charAt(i-1)-'0';
                if (digit%10==0){
                    if (j>0){
                        dp[i][j]=(dp[i][j]+dp[i-1][j-1])%1000000007;
                    } else if (digit % 5 == 0) {
                        dp[i][j]=(dp[i][j]+dp[i-1][j])%1000000007;
                    }
                }
            }

        }
        return dp[n][k];
    }
}