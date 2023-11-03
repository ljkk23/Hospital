public class MyStringBuilder {
    private char[] data;
    private Integer size;
    private Integer capacity;
    public MyStringBuilder(){
        this.data=new char[10];
        this.size =0;
        this.capacity=10;
    }
    public MyStringBuilder append(String x){
        int length = x.length();
        if (capacity-size<length){
            while(capacity-size<length){
                capacity=capacity*2;
            }
            char[] newChar=new char[capacity];

            for (int i = 0; i < size; i++) {
                newChar[i]=data[i];
            }
            for (int i = size,j=0; i <size+x.length(); i++,j++) {
                newChar[i]=x.charAt(j);
            }
            size+=x.length();
            data=newChar;
            return this;
        }
        for (int i = size,j=0; i <size+x.length(); i++,j++) {
            data[i]=x.charAt(j);
        }
        size+=x.length();
        return this;
    }

    public static void main(String[] args) {
        MyStringBuilder myStringBuilder=new MyStringBuilder();
        myStringBuilder.append("xxx");
        myStringBuilder.append("sss");
        myStringBuilder.append("");
        System.out.println("ddd");
    }
}
