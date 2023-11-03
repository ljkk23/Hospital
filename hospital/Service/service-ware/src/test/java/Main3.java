import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main3 {

    private static Map<Long,Long> resMap=new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer n=scanner.nextInt();
        long[] data=new long[n];
        for (int i = 0; i < n; i++) {
            data[i]=scanner.nextLong();
        }
        for(long i=0;i<10;i++){
            resMap.put(i,0l);
        }

        LinkedList<Long> dataList=new LinkedList<>();
        for (long tmp:data){
            dataList.addLast(tmp);
        }
        backTracing(dataList);

        for(long i=0;i<10;i++){
            Long aLong = resMap.get(i);
            System.out.println(aLong+" ");
        }

    }

    public static void backTracing(LinkedList<Long> data2){
        LinkedList<Long> data=new LinkedList<>(data2);
        if (data.size()==1){
            Long aLong = resMap.get(data.pollFirst());
            resMap.put(data.pollFirst(),aLong+1);
            return;
        }

        Long end1 = data.pollLast();
        Long end2 = data.pollLast();
        long newdata=(end2+ end1)%10;
        data.addLast(newdata);
        LinkedList<Long> tmp1=new LinkedList<>(data);
        backTracing(tmp1);

        data.removeLast();
        data.addLast(end2);
        data.addLast(end1);
        long newdata2=(end2*end1)%10;
        data.addLast(newdata2);
        LinkedList<Long> tmp2=new LinkedList<>(data);
        backTracing(tmp2);

    }
}