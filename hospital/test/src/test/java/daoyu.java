import java.util.*;

public class daoyu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // 读取换行符
        String str = scanner.nextLine();

        int minWeight = getMinWeight(n, str);
        System.out.println(minWeight);
    }

    private static int getMinWeight(int n, String str) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // 初始化每个字符的父节点为自身
        }

        // 遍历字符串，合并连通块
        for (int i = 1; i < n; i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                union(parent, i, i - 1);
            }
        }

        int weight = 0; // 连通块数量
        Set<Integer> rootSet = new HashSet<>(); // 用于记录根节点的集合
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            if (!rootSet.contains(root)) {
                rootSet.add(root);
                weight++;
            }
        }

        return weight;
    }

    private static void union(int[] parent, int i, int j) {
        int rootI = find(parent, i);
        int rootJ = find(parent, j);
        if (rootI != rootJ) {
            parent[rootI] = rootJ;
        }
    }

    private static int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }
}