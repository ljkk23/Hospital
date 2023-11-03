package tuhu2;

import java.util.*;


public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 
     * @param nums int整型一维数组 
     * @return int整型
     */
    public int validTriangle (int[] nums) {
        // write code here
        int count=0;
        int n=nums.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i+1; j < n - 1; j++) {
                for (int k = j+1; k < n; k++) {
                    if (isValidTra(nums[i],nums[j],nums[k])){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public static boolean isValidTra(int a,int b,int c){
        return a+b>c && a+c>b && b+c>a;
    }
}