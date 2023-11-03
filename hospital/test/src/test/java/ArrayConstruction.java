import java.util.Scanner;

public class ArrayConstruction {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the input values
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        // Calculate the sum of the array elements
        long sum = calculateMinSum(n, k);

        // Output the minimum sum
        System.out.println(sum);

        scanner.close();
    }

    private static long calculateMinSum(int n, int k) {
        // If n is 1, the minimum sum is k
        if (n == 1) {
            return k;
        }
        
        // If k is 1, the minimum sum is n
        if (k == 1) {
            return n;
        }
        
        // If k is greater than n, the minimum sum is n * k
        if (k > n) {
            return (long) n * k;
        }
        
        // If k is less than or equal to n, the minimum sum is k + (k * (n - 1))
        return (long) k + (k * (n - 1));
    }
}