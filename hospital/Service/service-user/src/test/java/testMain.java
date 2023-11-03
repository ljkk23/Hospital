import java.util.Arrays;

public class testMain {
    public static void main(String[] args) {
        int[] a={2,34,54,3,4,5,7,8};
        Arrays.sort(a);
        int target=7;
        getTargetFun(a,target);
    }
    public static int getTargetFun(int[] a,int target){
        int length = a.length;
        //左闭右闭[1,2,3,4,5,6,7]
        //left 0 right 4 [1,2,3]
        int left=0;
        int right=length-1;
        while (left<=right){
            int mid=(left+right)/2;
            if (a[mid]>target){
                right=mid-1;
            }else if (a[mid]<target){
                left=mid+1;
            }else if (a[mid]==target){
                return mid;
            }
        }
        return -1;
    }
}
