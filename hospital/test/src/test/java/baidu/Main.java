package baidu;

import net.sf.jsqlparser.statement.create.table.Index;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {

    private static final int RES_time=500;
    private static final int token=10;
    private static final int time_add=100;
    private static final int maxToken=150;

    private static HashMap<Integer,Integer> timeMap=new HashMap<>();

    public static void main(String[] args) {



    }
    public static int findShort(int[] nums,int target){
        int minlength=Integer.MAX_VALUE;
        int sum=0;
        int left=0;
        for (int right = 0; right < nums.length; right++) {
            sum+=nums[right];
            while (sum>=target){
                minlength=Math.min(minlength,right-left+1);
                sum-=nums[left];
                left++;
            }
        }
        return minlength==Integer.MAX_VALUE?0:minlength;
//
//        int totalLines = scanner.nextInt();
//        int tokens=100;
//        int limitCount=0;
//        int lastTime=0;
//        for (int i = 0; i < totalLines; i++) {
//            int requestTime = scanner.nextInt();
//            int requestCount = scanner.nextInt();
//            int tokenNeed=requestCount;
//            int timepassed=requestTime-lastTime;
//            int tokensGer=(timepassed/time_add)*token;
//            for (Map.Entry<Integer,Integer> entry:timeMap.entrySet()){
//                if (entry.getKey()<requestTime-500){
//                    tokensGer+=entry.getValue();
//                }
//            }
//            tokens=Math.min(tokens+tokensGer,maxToken);
//            int realCount=0;
//            if (tokens>=tokenNeed){
//                tokens-=tokenNeed;
//                realCount=requestCount;
//            }else {
//                int i1 = requestCount - tokens;
//                limitCount+=i1;
//                realCount=tokens;
//            }
//            lastTime=requestTime;
//            timeMap.put(requestTime,realCount);
//        }
//        System.out.println(limitCount);



//        Scanner scanner = new Scanner(System.in);
//        String[] split = scanner.nextLine().split(",");
//        String s=split[0];
//        Integer numRows=Integer.parseInt(split[1]);
//        int n=s.length();
//        int r=numRows;
//        if (r==1 || r>=n){
//            System.out.println(s);
//            return;
//        }
//        int t=r*2-2;
//        int c=(n+t-1)/t*(r-1);
//        char[][] mat=new char[r][c];
//        for (int i = 0,x=0,y=0; i <n; i++) {
//            mat[x][y]=s.charAt(i);
//            if (i%t<r-1){
//                ++x;
//            }else {
//                --x;
//                ++y;
//            }
//        }
//        StringBuffer ans=new StringBuffer();
//        for (char[] row:mat){
//            for (char ch:row){
//                if (ch!=0){
//                    ans.append(ch);
//                }
//            }
//        }
//        System.out.println(ans.toString());
    }

}
