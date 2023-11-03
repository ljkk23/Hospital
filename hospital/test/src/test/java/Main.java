import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int length = scanner.nextInt();
        Long[] data=new Long[length];
        long totalDistance=0;
        for (int i = 0; i <length-1; i++) {
            data[i]=scanner.nextLong();
            totalDistance+=data[i];
        }
        data[length-1]=scanner.nextLong();
        totalDistance+=data[length-1];
        int x=scanner.nextInt();
        int y=scanner.nextInt();
        long clockdis1=0;
        long counterdis2=0;
        if (x<y){
            for (int i = x-1; i <y-1; i++) {
                clockdis1+=data[i];
            }
            counterdis2=totalDistance-clockdis1;
        }else {
            for (int i = y-1; i <x-1; i++) {
                counterdis2+=data[i];
            }
            clockdis1=totalDistance-counterdis2;
        }
        long min = Math.min(clockdis1, counterdis2);
        System.out.println(min);
    }
//    public Integer getStartToEnd(int[] data,Integer startIndex,Integer endIndex){
//        int res=0;
//        for (int i = startIndex; i <endIndex; i++) {
//            res+=data[i-1];
//        }
//    }
}