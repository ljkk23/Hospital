import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {

    static List<List<Integer>> adj;
    static int[] dis;

    static int[] degree;
    static  int maxCount=0;

    public static void dfs(int node,int depth,int k){
        if (depth<=k){
            maxCount++;
        }

        for (int nei : adj.get(node)){
            if (dis[nei]==-1){
                degree[nei]--;
                if (degree[nei]==1){
                    dis[nei]=depth+1;
                }else {
                    dis[nei]=depth;
                }
                dfs(nei,dis[nei],k);
            }
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        adj=new ArrayList<>();
        for (int i = 0; i <=n; i++) {
            adj.add(new ArrayList<>());
        }
        dis=new int[n+1];
        degree=new int[n+1];
        for (int i = 0; i < n - 1; i++) {
            int u=scanner.nextInt();
            int v=scanner.nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }



        for (int i = 0; i <= n; i++) {
            dis[i]=-1;
        }
        dis[1]=0;
        dfs(1,0,k);
        System.out.println(maxCount);



    }
}