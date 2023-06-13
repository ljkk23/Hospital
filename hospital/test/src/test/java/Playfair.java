import java.util.ArrayList;
import java.util.List;
public class Playfair {

    /**
     * 处理明文，重复字母中间插X，长度非偶数添加X
     * @param P 密文
     * @return 处理后的明文
     */
    public static String dealP(String P){
        P=P.toUpperCase();// 将明文转换成大写
        P=P.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        StringBuilder sb=new StringBuilder(P);
        for(int i=1;i<sb.length();i=i+2){
            //一对明文字母如果是重复的则在这对明文字母之间插入一个填充字符
            if(sb.charAt(i)==sb.charAt(i-1)){
                sb.insert(i,'X');
            }
        }
        //如果经过处理后的明文长度非偶数，则在后面加上字母x
        if(sb.length()%2!=0){
            sb.append('X');
        }
        String p=sb.toString();
        System.out.println("处理后的明文："+p);
        return p;
    }

    /**
     * 处理密钥，将J转换成I，除去重复字母
     * @param K 密钥
     * @return 修改后的密钥List<Character>
     */
    public static List<Character> dealK(String K){
        K=K.toUpperCase();// 将密钥转换成大写
        K=K.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        K=K.replaceAll("J","I");//将J转换成I
        List<Character> list=new ArrayList<Character>();
        char[] ch=K.toCharArray();
        int len=ch.length;
        for(int i=0;i<len;i++){
            //除去重复出现的字母
            if(!list.contains(ch[i])){
                list.add(ch[i]);
            }
        }
        System.out.println("处理后的密钥："+list);
        return list;
    }

    /**
     * 将密玥的字母逐个加入5×5的矩阵内，剩下的空间将未加入的英文字母
     * 依a-z的顺序加入。（将I和J视作同一字。JOY -> IOY）
     * @param K 密钥
     * @return 5*5矩阵
     */
    public static char[][] matrix(String K){
        List<Character> key=dealK(K);
        //添加在K中出现的字母
        List<Character> list=new ArrayList<Character>(key);
        //添加按字母表顺序排序的剩余的字母
        for(char ch='A';ch<='Z';ch++){
            if(ch!='J'&&!list.contains(ch)){
                list.add(ch);
            }
        }
        char[][] matrix=new char[5][5];
        int count=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                matrix[i][j]=list.get(count++);
            }
        }
        System.out.println("根据密钥'"+K+"'构建的矩阵：");
        showMatrix(matrix);
        return matrix;
    }

    /**
     * 打印矩阵
     * @param matrix 矩阵
     */
    public static void showMatrix(char[][] matrix){
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     * 根据playfair算法对明文对进行加密
     * @param matrix 矩阵
     * @param ch1 字母1
     * @param ch2 字母2
     * @return 密文对
     */
    public static String encrypt(char[][] matrix,char ch1,char ch2){
        //获取明文对在矩阵中的位置
        int r1=0,c1=0,r2=0,c2=0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(ch1==matrix[i][j]){
                    r1=i;
                    c1=j;
                }
                if(ch2==matrix[i][j]){
                    r2=i;
                    c2=j;
                }
            }
        }
        StringBuilder sb=new StringBuilder();
        //进行规制匹配，得到密文对
        if(r1==r2){
            //明文字母对的两个字母在同一行，则截取右边的字母
            sb.append(matrix[r1][(c1+1)%5]);
            sb.append(matrix[r1][(c2+1)%5]);
        }else if(c1==c2){
            //明文字母对的两个字母在同一列，则截取下方的字母
            sb.append(matrix[(r1+1)%5][c1]);
            sb.append(matrix[(r2+1)%5][c1]);
        }else{
            //明文字母所形成的矩形对角线上的两个字母，任意选择两种方向
            //明文对中的每一个字母将由与其同行，且与另一个字母同列的字母代替
            sb.append(matrix[r1][c2]);
            sb.append(matrix[r2][c1]);
            //sb.append(matrix[r2][c1]);
            //sb.append(matrix[r1][c2]);
        }
        sb.append(' ');
        return sb.toString();
    }

    /**
     * 对明文进行加密
     * @param P 明文
     * @param K 密钥
     * @return 密文
     */
    public static String encrypt(String P,String K){
        char[] ch=dealP(P).toCharArray();
        char[][] matrix=matrix(K);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<ch.length;i=i+2){
            sb.append(encrypt(matrix,ch[i],ch[i+1]));
        }
        return sb.toString();
    }

    /**
     * 根据playfair算法对密文对进行解密
     * @param matrix
     * @param ch1 字母1
     * @param ch2 字母2
     * @return 明文对
     */
    public static String decrypt(char[][] matrix,char ch1,char ch2){
        //获取密文对在矩阵中的位置
        int r1=0,c1=0,r2=0,c2=0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(ch1==matrix[i][j]){
                    r1=i;
                    c1=j;
                }
                if(ch2==matrix[i][j]){
                    r2=i;
                    c2=j;
                }
            }
        }
        StringBuilder sb=new StringBuilder();
        //进行规制匹配，得到明文对
        if(r1==r2){
            //密文字母对的两个字母在同一行，则截取左边的字母
            sb.append(matrix[r1][(c1-1+5)%5]);
            sb.append(matrix[r1][(c2-1+5)%5]);
        }else if(c1==c2){
            //密文字母对的两个字母在同一列，则截取上方的字母
            sb.append(matrix[(r1-1+5)%5][c1]);
            sb.append(matrix[(r2-1+5)%5][c1]);
        }else{
            //密文字母所形成的矩形对角线上的两个字母，任意选择两种方向
            sb.append(matrix[r1][c2]);
            sb.append(matrix[r2][c1]);
            //sb.append(matrix[r2][c1]);
            //sb.append(matrix[r1][c2]);
        }
        sb.append(' ');
        return sb.toString();
    }

    /**
     * 对密文进行解密
     * @param C 密文
     * @param K 密钥
     * @return 明文
     */
    public static String decrypt(String C,String K){
        C=C.toUpperCase();
        C=C.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        char[] ch=C.toCharArray();
        char[][] matrix=matrix(K);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<ch.length;i=i+2){
            sb.append(decrypt(matrix,ch[i],ch[i+1]));
        }
        return sb.toString();
    }

    public static void main(String args[]){
//        String P="session";
//        //String P="Hide the gold in the tree stump";// 明文
//        //String P="benrencainiaoyizhi";
//        //String P="balloon";
//        String K="playfair";// 密钥
//        //String K="playfair example";
//        //String K="monarchy";
//        String C="";// 密文
//        if(K.length()<=25){
//            C=encrypt(P,K);
//            System.out.println("加密后的密文："+C);
//            P=decrypt(C,K);
//            System.out.println("解密后的明文："+P);
//        }else{
//            System.out.println("密钥大于25个字符！！");
//        }
        String plaintext="COMPUTER";
        String key="acm";
        String encryption = Encryption(plaintext, key);
        System.out.println(encryption);
    }

    public static String Encryption(String plaintext, String vigekey) {

        //  声明一个空字符串用来存放加密后的密文
        String cipher = "";
        //  for循环对字符串中每个字符进行加密操作
        for (int i = 0; i < plaintext.length(); i++) {
            // 字符变量用于存放依次取出的字符串中的字符
            char p = plaintext.charAt(i);
            // 取出明文中每个字符对应的密钥
            char v = vigekey.charAt(i % vigekey.length());
            if (p >= 'A' && p <= 'Z') {// 明文中的大写字母的转换
                if (v >= 'A' && v <= 'Z') {// 密钥是大写字母
                    p = (char) (((p - 'A') + (v - 'A')) % 26 + 'A');
                } else if (v >= 'a' && v <= 'z') {// 密钥是小写字母
                    p = (char) (((p - 'A') + (v - 'a')) % 26 + 'A');
                } else if (v >= '0' && v <= '9') {// 密钥是数字
                    p = (char) (((p - 'A') + (v - '0')) % 26 + 'A');
                }
            } else if (p >= 'a' && p <= 'z') {//  明文是小写字母的转换
                if (v >= 'A' && v <= 'Z') {// 密钥是大写字母
                    p = (char) (((p - 'a') + (v - 'A')) % 26 + 'a');
                } else if (v >= 'a' && v <= 'z') {// 密钥是小写字母
                    p = (char) (((p - 'a') + (v - 'a')) % 26 + 'a');
                } else if (v >= '0' && v <= '9') {// 密钥是数字
                    p = (char) (((p - 'a') + (v - '0')) % 26 + 'a');
                }
            } else if (p >= '0' && p <= '9') {// 明文是数字的转换
                if (v >= 'A' && v <= 'Z') {// 密钥是大写字母
                    p = (char) (((p - '0') + (v - 'A')) % 10 + '0');
                } else if (v >= 'a' && v <= 'z') {// 密钥是小写字母
                    p = (char) (((p - '0') + (v - 'a')) % 10 + '0');
                } else if (v >= '0' && v <= '9') {// 密钥是数字
                    p = (char) (((p - '0') + (v - '0')) % 10 + '0');
                }
            }
            cipher += p;
        }

        return cipher;

    }
}
