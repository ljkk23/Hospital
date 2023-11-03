package yitu;

    import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < n; i++) {
//            dataMap.put(nums[i], i);
//        }
//        HashMap<Integer,Integer> dataMap=new HashMap<>();
//        for (int i = 0; i < count; i++) {
//            int tmpData = in.nextInt();
//            dataMap.put(tmpData,i);
//        }
//        TreeMap<Integer,Integer> treeMap=new TreeMap<>();
//        for (Map.Entry<Integer,Integer> entry:dataMap.entrySet()){
//            treeMap.put(entry.getValue(), entry.getKey());
//        }
//        for (Map.Entry<Integer,Integer> entry: treeMap.entrySet()){
//            System.out.print(entry.getValue()+" ");
//        }

    }

    public int[] findQ (int n, int[] nums) {

        HashMap<Integer, Integer> dataMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            dataMap.put(nums[i], i);
        }
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (Map.Entry<Integer, Integer> entry : dataMap.entrySet()) {
            treeMap.put(entry.getValue(), entry.getKey());
        }
        int[] res=new int[treeMap.size()];
        int i=0;
        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            res[i++]=entry.getValue();
        }
        return res;

    }

    public int find (int n, int v, int[] a) {
        // write code here
        int left=0;
        int right=a.length-1;
        int res=n+1;
        while (left<=right){
            int mid=left+(right-left)/2;
            if (a[mid]==v){
                res=mid;
                left=mid+1;
            }else if (a[mid]<v){
                left=mid+1;
            }else {
                right=mid-1;
            }
        }
        return res;

    }

//    public int lengthL (ListNode head) {
//        // write code here
//        HashSet<Integer> set=new HashSet<>();
//        int length=0;
//        ListNode cur=head;
//        while (cur!=null){
//            if (!set.contains(cur.value)){
//                set.add(cur.value);
//                length++;
//            }
//            cur=cur.next;
//        }
//        return length;
//    }
}