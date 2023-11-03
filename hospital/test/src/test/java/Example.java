public class Example {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set("Value"); // 设置当前线程的局部变量副本

        String value = threadLocal.get(); // 获取当前线程的局部变量副本
        System.out.println(value); // 输出: Value

        threadLocal.remove(); // 移除当前线程的局部变量副本
    }
}