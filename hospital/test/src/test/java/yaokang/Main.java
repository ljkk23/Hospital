package yaokang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    private static int count;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        List<Integer> values=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            values.add(scanner.nextInt());
        }
        List<List<Integer>> gra=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            gra.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int u=scanner.nextInt()-1;
            int v=scanner.nextInt()-1;
            gra.get(u).add(v);
            gra.get(v).add(u);
        }
        count=0;
        boolean[] visited=new boolean[n];
        dfs(0,-1,values,gra,visited);
        System.out.println(count);
    }

    private static void dfs(int node,int parent,List<Integer> values,List<List<Integer>> gra
    ,boolean[] visited){
        visited[node]=true;
        for (int nei : gra.get(node)){
            if (!visited[nei] && isPer(values.get(node)*values.get(nei))){
                count+=2;
                visited[nei]=true;
            }
        }
        for (int nei:gra.get(node)){
            if (!visited[nei]){
                dfs(nei,node,values,gra,visited);
            }
        }
    }
    private static boolean isPer(int sum){
        int sqrt=(int) Math.sqrt(sum);
        return sqrt*sqrt==sum;
    }
}