package yaokang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class calender {
    public static void main(String[] args) {
        int year=2022;
        int month=3;
        int dayofweek=3;
        int allDays=0;
        Map<Integer,Integer> mymap=new HashMap<>();


        List<String[]> res=new ArrayList<>();
        switch(month){
            case 2:
                if (year%400==0 || (year%2==0 && year%100!=0)){
                    allDays=29;
                }else allDays=28;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                allDays=31;
            default:
                allDays=30;
        }
        String[] first=new String[7];
        for (int i = 0; i < 7; i++) {
            if (i<dayofweek-1){
                first[i]=" ";
            }else {
                first[i]=String.valueOf(i);
            }
        }
        res.add(first);


    }
}
