import java.util.Map;
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main2 {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        long n = scanner.nextLong();
//        long m = scanner.nextLong();
//        long startX=scanner.nextLong();
//        long startY=scanner.nextLong();
//        long X1=scanner.nextLong();
//        long Y1=scanner.nextLong();
//        long X2=scanner.nextLong();
//        long Y2=scanner.nextLong();
//
//
//        long Xdis1=Math.min(Math.abs(X1-startX),Math.min(startX,X1)+m-Math.max(startX,X1));
//        long Ydis1=Math.min(Math.abs(Y1-startY),Math.min(startY,Y1)+n-Math.max(startY,Y1));
//
//        long Xdis2=Math.min(Math.abs(X2-X1),Math.min(X1,X2)+m-Math.max(X1,X2));
//        long Ydis2=Math.min(Math.abs(Y2-Y1),Math.min(Y1,Y2)+n-Math.max(Y1,Y2));
//
//        System.out.println(Xdis1+Ydis1+Xdis2+Ydis2);

        Scanner scanner=new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();

        long x1 = scanner.nextLong();
        long y1 = scanner.nextLong();

        long x2 = scanner.nextLong();
        long y2 = scanner.nextLong();

        long x3 = scanner.nextLong();
        long y3 = scanner.nextLong();

        long steps1= Math.abs(x1-x2)+Math.abs(y1-y2);

        long step2;

        if (x2==x3 || y2==y3){
            step2=Math.abs(x2-x3)+Math.abs(y2-y3);
        }else {
            step2=Math.abs(x2-x3)+Math.abs(y2-y3)+1;
        }
        long total=steps1+step2;
        System.out.println(total);

    }
}