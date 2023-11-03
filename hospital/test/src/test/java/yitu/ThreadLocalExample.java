package yitu;

public class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) {
        int[] nums={4,6,1,2};
        System.out.println(maximumScore(nums, 2));
    }

    public static int subsequence (String source, String pattern) {
        // write code here
        int res=0;
        int pLength = pattern.length();
        for (int i = 0; i < source.length(); i++) {
            int pLeft=0;
            int sLeft=i;
            while (true){
                if (pLeft>=pLength){
                    res++;
                    break;
                }
                if (sLeft>=source.length()){
                    break;
                }

                if (source.charAt(sLeft)==pattern.charAt(pLeft)){
                    sLeft++;
                    pLeft++;
                }else if (sLeft==i){
                    break;
                }else {
                    sLeft++;
                }
            }
        }
        return res;
    }

    public static int maximumScore (int[] nums, int k) {
        // write code here
        int res=0;
        for (int i = 0; i < nums.length; i++) {
            int data=nums[i]; 
            int count=0;
            for (int j = 0; j <nums.length; j++) {
                if (nums[j]>=data-k && nums[j]<=data+k){
                    count++;
                }
            }
            res=Math.max(res,count);
        }
        return res;
    }
}