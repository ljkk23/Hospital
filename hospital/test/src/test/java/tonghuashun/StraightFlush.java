package tonghuashun;

import java.util.LinkedList;

public class StraightFlush {

    public static void main(String[] args) {
        //请在此处完成程序并将结果以System.out.println()输出
        int[] nums1={1,2,3,0,0,0};
        int[] nums2={2,5,6};
        int len1=nums1.length;
        int len2=nums2.length;
        int aleft=0;
        int bleft=0;
        LinkedList<Integer> res=new LinkedList<>();
        while(aleft<len1 && bleft<len2){
            System.out.println("fff"+aleft);
            System.out.println(bleft);
            if(nums1[aleft]>nums2[bleft]){
                res.add(nums2[bleft]);
                bleft++;
            }else{
                res.add(nums1[aleft]);
                aleft++;
            }
        }
        while(aleft<len1 ){
            res.add(nums1[aleft]);
            aleft++;
        }
        while(bleft<len2){
            res.add(nums2[bleft]);
            bleft++;
        }
        int i=0;
        for(Integer data:res){
            System.out.println(data);
            nums1[i]=data;
            i++;
        }
        System.out.println(nums1);

    }
}