import java.util.Arrays;

public class leetcode {

    public static void main(String[] args) {
        int[] array = {6,72,113,11,23,50};
        quickSort(array, 0, array.length -1);
        System.out.println("排序后的结果");
        System.out.println(Arrays.toString(array));
    }

    private static void quickSort(int[] array, int low, int high) {
        if (!(low<high)){
            return;
        }
        int pivot=array[high];
        int pointer=low;
        for (int i = low; i < high; i++) {
            if (array[i]<=pivot){
                int tmp=array[i];
                array[i]=array[pointer];
                array[pointer]=tmp;
                pointer++;
            }
        }
        int tmp=array[high];
        array[high]=array[pointer];
        array[pointer]=tmp;
        quickSort(array,low,pointer-1);
        quickSort(array,pointer+1,high);
    }
}
