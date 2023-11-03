import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class maintest {
    private static boolean[] used;
    private static List<List<Integer>> res=new ArrayList<>();
    private static LinkedList<Integer> tmp=new LinkedList<>();
    public static void main(String[] args) {
        int[] a={1,2,3};
        int length = a.length;
        used=new boolean[length];
        backTracing(a,length);
        System.out.println(res);
    }
    public static void backTracing(int[] a,int length){
        if (tmp.size()==length){
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < length; i++) {
            if (used[i]){
                continue;
            }
            tmp.addLast(a[i]);
            used[i]=true;
            backTracing(a,length);
            tmp.removeLast();
            used[i]=false;
        }
    }
}
