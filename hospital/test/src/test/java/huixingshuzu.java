import java.util.ArrayList;
import java.util.List;

public class huixingshuzu {
    public static void main(String[] args) {
        int[][] matrix=new int[3][2];
        matrix[0][0]=2;
        matrix[0][1]=5;
        matrix[1][0]=8;
        matrix[1][1]=4;
        matrix[2][0]=0;
        matrix[2][1]=-1;

        int rows= matrix.length;
        int column=matrix[0].length;
        int left=0;
        int right=column-1;
        int up=0;
        int blow=rows-1;
        int dataSize=rows*column;
        List<Integer> res=new ArrayList<>();
        while(blow>=up &&right>=left){
            for (int j = left; j <=right; j++) {
                res.add(matrix[up][j]);
            }
            up++;
            for (int j = up; j <=blow; j++) {
                res.add(matrix[j][right]);
            }
            right--;
            if(blow>up &&right>left){

                for (int j = right; j >= left; j--) {
                    res.add(matrix[blow][j]);
                }
                blow--;
                for (int j = blow; j >=up; j--) {
                    res.add(matrix[j][left]);
                }
                left++;
            }

        }
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
    }
}
