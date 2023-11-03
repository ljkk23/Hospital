package xiaomi;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String[] firstLine = scanner.nextLine().split(",");
        int n=Integer.parseInt(firstLine[0]);
        int radius=Integer.parseInt(firstLine[1]);
        int xMin=Integer.MAX_VALUE;
        int xMax=Integer.MIN_VALUE;
        int yMin=Integer.MAX_VALUE;
        int yMax=Integer.MIN_VALUE;
        Map<List<Integer>,Integer> dataMap=new HashMap<>();
        for (int i = 0; i < n; i++) {
            String[] split = scanner.nextLine().split(",");
            xMin=Math.min(xMin,Integer.parseInt(split[0]));
            xMax=Math.max(xMax,Integer.parseInt(split[0]));
            yMin=Math.min(yMin,Integer.parseInt(split[1]));
            yMax=Math.max(yMax,Integer.parseInt(split[1]));
            LinkedList<Integer> list=new LinkedList<>();
            list.addLast(Integer.parseInt(split[0]));
            list.addLast(Integer.parseInt(split[1]));
            dataMap.put(list,Integer.parseInt(split[2]));
        }

        int resX = Integer.MAX_VALUE;
        int resY=Integer.MAX_VALUE;
        int resxinhao = Integer.MIN_VALUE;
        for (int i = xMax; i>= xMin; i--) {
            for (int j = yMax; j >=yMin; j--) {
                int xinhao=0;
                for (Map.Entry<List<Integer>,Integer> entry: dataMap.entrySet()){
                    List<Integer> listKey = entry.getKey();
                    Integer x = listKey.get(0);
                    Integer y = listKey.get(1);
                    int longx=Math.abs(x-i);
                    int longY=Math.abs(y-j);
                    int i1 = longx * longx + longY * longY;
                    double sqrted = Math.sqrt(i1);
                    if (longx*longx+longY*longY<=radius*radius){
                        double v = entry.getValue() / (1 + sqrted);
                        xinhao+=v;
                    }
                }
                if (xinhao>resxinhao){
                    resxinhao=xinhao;
//                    if (i<resX){
//                        resX=i;
//                        resY=j;
//                    } else if (i == resX) {
//                        if (j<resY){
//                            resX=i;
//                            resY=j;
//                        }
//                    }
                    resX=i;
                    resY=j;
                }

            }
        }
        System.out.print(resX+","+resY);



    }

}
