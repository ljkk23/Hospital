import org.springframework.beans.factory.annotation.Autowired;

public class rabbitMqTest {

    public static void main(String[] args) {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWight = 4;
        testWeightBagProblem(weight, value, bagWight);
    }

    public static void testWeightBagProblem(int[] weight, int[] value, int bagWight){
        int[] dp=new int[bagWight+1];

        for(int i=0;i< weight.length;i++){
            for(int j=bagWight;j>=weight[i];j--){
                dp[j]=Math.max(dp[j],dp[j-weight[i]]+value[i]);
            }
        }
        for (int data: dp){
            System.out.println(data);
        }

    }
}
