

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int[] nums=new int[]{1,5,6,9,7,2,10};
        List<List<Integer>> res= new ArrayList<>();
        int target=8;
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i <length; i++) {
            if (i>0&&nums[i]==nums[i-1]){
                continue;
            }

            int left=i+1;
            int right=length-1;
            while(left<right){
                int sum=nums[i]+nums[left]+nums[right];
                if (sum>target){
                    right--;
                } else if (sum<target) {
                    left++;
                } else if (sum == target) {
                    List<Integer> tmp=new ArrayList<>();
                    tmp.add(nums[i]);
                    tmp.add(nums[left]);
                    tmp.add(nums[right]);
                    res.add(tmp);
                    left++;
                    right--;
                    while(nums[right]==nums[right-1]){
                        right--;
                    }
                    while(nums[left]==nums[left+1]){
                        left++;
                    }

                }

            }

        }
        for (List<Integer> tmp : res) {
            System.out.println(tmp.toString());
        }

    }
}
