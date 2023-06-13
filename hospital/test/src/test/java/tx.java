public class tx {
    public static void main(String[] args) {
        String s1=new String("123Xabchu");
        int a=3;
        String change = change(3, s1);
        System.out.println(change);
    }

    public static String change(int index,String s1){
        char[] tmp=new char[index];
        for (int i = 0; i < index; i++) {
            tmp[i]=s1.charAt(i);
        }
        String substring = s1.substring(index);
        String tmp1=String.valueOf(tmp);
        return substring+tmp1;

    }
}
