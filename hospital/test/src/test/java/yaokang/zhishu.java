package yaokang;

import java.util.ArrayList;
import java.util.List;

public class zhishu {

    public static void main(String[] args) {
        int a=515;
        List<int[]> res=new ArrayList<int[]>();
        int i=2;
        while (a!=1){
            if (a%i==0){
                int[] tmp=new int[2];
                tmp[0]=i;
                tmp[1]=1;
                a=a/i;
                while (a%i==0){
                    tmp[1]++;
                    a=a/2;
                }
                res.add(tmp);
            }else {
                i++;
            }
        }
        for (int[] data :
                res) {
            System.out.println(data[0]);
            System.out.println(data[1]);
        }
    }
}
