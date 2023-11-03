package xiecheng;
import java.util.*;


public class Main {
     static LinkedList<Integer> tmp=new LinkedList<>();
     static LinkedList<Integer> res;
     static int first=Integer.MAX_VALUE;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] A=new int[n];
        for (int i = 0; i < n; i++) {
            A[i]=in.nextInt();
        }
        boolean[] used=new boolean[n];
        backTrac(A,n,used);
        for (int i = 0; i < res.size(); i++) {
            System.out.print(res.get(i)+" ");
        }
    }
    public static boolean backTrac(int[] A,int n,boolean[] used){
        if (tmp.size()==n){
//            System.out.println("DDD");
//            for (Integer data:tmp){
//                System.out.print(data);
//            }

            if (checkA(A)){
                res=new LinkedList<>(tmp);
                return true;
            }else {
                return false;
            }
        }
        for (int i = 0; i < n; i++) {
            if (used[i]){
                continue;
            }
            tmp.addLast(i+1);
            used[i]=true;
            boolean b = backTrac(A, n, used);
            if (b){
                return true;
            }
            tmp.removeLast();
            used[i]=false;
        }
        return false;
    }
    public static boolean checkA(int[] a){

        for (int i = 0; i < tmp.size(); i++) {
            if (tmp.get(i) == a[i]) {
                return false;
            }
        }
        return true;

    }
}
