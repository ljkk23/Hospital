package daying;

import java.util.concurrent.*;

public class ThreadDemo {

    static class Callthread implements Callable<Integer>{
        private int data;
        public Callthread(int data){
            this.data=data;
        }
        @Override
        public Integer call() throws Exception {
            System.out.println("a正在运行");
            return data;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ;
//        Callthread aCall=new Callthread(1);
//        FutureTask<Integer> futureTask=new FutureTask<>(aCall);
//        Thread aThread=new Thread(futureTask);
//        aThread.start();
//        Callthread bCall=new Callthread(2);
//        FutureTask<Integer> futureTask1=new FutureTask<>(bCall);
//        Thread bThread=new Thread(futureTask1);
//
//        bThread.start();
//
//
//        Callthread cThread=new Callthread(3);
//        FutureTask<Integer> futureTask2=new FutureTask<>(cThread);
//        Thread dThread=new Thread(futureTask2);
//        dThread.start();
        ExecutorService executorService =Executors.newSingleThreadExecutor();
        Callthread callthread=new Callthread(1);
        Callthread callthread2=new Callthread(2);
        Future<Integer> future=executorService.submit(callthread);
        Future<Integer> future2=executorService.submit(callthread2);
        Integer integer = future.get();
        Integer integer1 = future2.get();
        System.out.println(integer1+integer);


    }
}
