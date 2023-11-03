import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int subjectCount = scanner.nextInt();
        int timeAll = scanner.nextInt();
        int peoAll = scanner.nextInt();
        int[][] subjectArr=new int[subjectCount][2];
        long[] happyGet=new long[subjectCount];
        for (int i = 0; i <subjectCount; i++) {
            subjectArr[i][0]=scanner.nextInt();
            subjectArr[i][1]=scanner.nextInt();
            //subjectArr[i][2]=scanner.nextInt();
            happyGet[i]=scanner.nextLong();
        }
        long[][][] dp=new long[subjectCount+1][timeAll+1][peoAll+1];

        for (int i = 1; i <=subjectCount; i++) {
            for (int j = 0; j <=timeAll; j++) {
                for (int k = 0; k <=peoAll; k++) {
                    if (subjectArr[i-1][0]<=j && subjectArr[i-1][1]<=k){
                        dp[i][j][k]=Math.max(dp[i-1][j][k],dp[i-1][j-subjectArr[i-1][0]]
                                [k-subjectArr[i-1][1]]+happyGet[i-1]);
                    }else {
                        dp[i][j][k]=dp[i-1][j][k];
                    }
                }
            }
        }
        System.out.println(dp[subjectCount][timeAll][peoAll]);
    }
}
