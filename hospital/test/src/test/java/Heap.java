public class Heap {

    public static void main(String[] args) {
        int i;
        int a[] = {20,30,90,40,70,110,60,10,100,50,80};

        System.out.printf("before sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        heapSortAsc(a,a.length);            // 升序排列
        //heapSortDesc(a, a.length);        // 降序排列

        System.out.printf("after  sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");
    }
    public static void  heapSortAsc(int[] a, int aLen){

        for (int i = aLen; i >0; i--) {
            changeMaxHeap(a,i/2,aLen);
        }
        for (int i = aLen-1; i >0; i--) {
            int tmp=a[i];
            a[i]=a[0];
            a[0]=tmp;
            changeMaxHeap(a,0,i-1);
        }
    }
    public static void changeMaxHeap(int[] a, int cur,int end){

        System.out.println(cur);
        int max=Integer.MIN_VALUE;
        while (cur*2+1<end){
            int left=cur*2+1;
            int right=cur*2+2;
            if (a[left]>=a[right]){
                max=left;
            }else {
                max=right;
            }
            int tmp=a[cur];
            if (tmp>=a[max]){
                return;
            }
            a[cur]=a[max];
            a[max]=tmp;
            cur=max;
        }
    }
}
