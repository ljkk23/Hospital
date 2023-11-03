import java.util.Scanner;

public class MinSumArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int k = scanner.nextInt();
        scanner.close();

        // 构造满足条件的数组
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = k * (i + 1);
        }

        // 计算数组元素之和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        System.out.println(sum);
    }
}