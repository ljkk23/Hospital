package dipingxian;

import javax.swing.plaf.IconUIResource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String s = scanner.next();
        LinkedList<Character> list=new LinkedList<>();
        char[] charArray = s.toCharArray();
        char pre=s.charAt(0);
        int index=0;
        StringBuilder stringBuilder=new StringBuilder();
        int count=0;
        for (int i = 0; i < s.length(); i++) {
            if (i==0){
                continue;
            }
            if (s.charAt(i)==s.charAt(i-1)){
                count++;
            }else {
                if (count==0){
                    stringBuilder.append(s.charAt(i-1));
                }else {
                    stringBuilder.append(s.charAt(i-1)+String.valueOf(count+1));
                }
                count=0;
            }
            if (i==s.length()-1){
                stringBuilder.append(s.charAt(i)+String.valueOf(count+1));
            }

        }

        System.out.println(stringBuilder.toString());
    }

    public int maxArea (int[] height) {
        // write code here
        int max=0;
        int l=0;
        int r=height.length-1;
        while (l<r){
            max=Math.max(max,Math.min(height[l],height[r])*(r-1));
            if (height[l]<height[r]){
                l++;
            }else{
                r--;
            }
        }
        return max;
    }
}
