package yaokang;
import java.util.*;
public class saojiaoxing {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] data=new int[n][2];
        for(int i=0;i<n;i++){
            data[i][0]=scanner.nextInt();
            data[i][1]=scanner.nextInt();
        }
//        System.out.println(Math.sqrt(4));
        int res=0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    int x1=data[i][0]-data[j][0];
                    int y1=data[i][1]-data[j][1];
                    int x2=data[i][0]-data[k][0];
                    int y2=data[i][1]-data[k][1];
                    if (x1*y2!=x2*y1){
                        res++;
                    }
                }
            }
        }
        System.out.println(res);
    }
}
