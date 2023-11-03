package tonghuashun;

import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        int[] a={1,3,5,7,9};
        int[] b={1,2,3,4,5,6,7};
        LinkedList<Integer> res=new LinkedList<>();
        int aLeft=0;
        int bLeft=0;
        while (aLeft!=a.length && bLeft!=b.length){
            if (a[aLeft]>b[bLeft]){
                res.addLast(b[bLeft]);
                bLeft++;
            }else if (a[aLeft]==b[bLeft]){
                res.addLast(a[aLeft]);
                aLeft++;
                bLeft++;
            }else {
                res.addLast(a[aLeft]);
                aLeft++;
            }
        }
        while (aLeft!=a.length){
            res.addLast(a[aLeft]);
            aLeft++;
        }
        while (bLeft!=b.length){
            res.addLast(b[bLeft]);
            bLeft++;
        }
        for (Integer data:res){
            System.out.println(data);
        }

    }
}
