package com.fanjiangqi;

import java.util.concurrent.*;

/**
 * Created by fanjiangqi on 2017/3/19.
 * 多线程练习记忆
 */
class MyThread extends Thread{
    private int tid;

    public MyThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; ++i){
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(String.format("T%d:%d",tid,i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
   private final BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
            try {
                for (int i = 0; i < 20; ++i) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    queue.put(String.valueOf(i));
                    System.out.println(String.format("producer:%d",i));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
class Comsumer implements Runnable{
    private final BlockingQueue<String> queue;

    public Comsumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(String.format("%s:%s",Thread.currentThread().getName(),queue.take()));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

public class MultiThread {
    public static void testThread(){
        for (int i = 0; i < 10; ++i){
          //  new Thread(new MyThread(i)).start();
        }
        for (int i = 0; i < 10; ++i){
            final int tid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; ++j){
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                            System.out.println(String.format("T%d:%d",tid,j));
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
    private static Object object = new Object();
    public static void testSynchronized1(){
        synchronized (object){
            try {
                for (int i = 0; i < 10; ++i){
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(String.format("T%s:%d",Thread.currentThread().getName(),i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void testSynchronized2(){
        synchronized (object){
            try {
                for (int i = 0; i < 10; ++i){
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(String.format("T%s:%d",Thread.currentThread().getName(),i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void testSynchronized(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                testSynchronized1();
            }
        },"thread 1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testSynchronized2();
            }
        },"thread 2").start();
    }
    public static void sleep(int milli){
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static ThreadLocal<Integer> threadLocalUserId = new ThreadLocal<>();
    private static int userId;
    public static void testThreadLocal(){

        for (int i = 0; i < 10; ++i){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocalUserId.set(finalI);
                    sleep(1000);
                   // System.out.println("Thread"+finalI+":"+threadLocalUserId.get());
                    System.out.println(String.format("Thread %d : %d",finalI,threadLocalUserId.get()));
                }
            }).start();
        }

       /* for (int i = 0; i < 10; ++i){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userId = finalI;
                    sleep(1000);
                    // System.out.println("Thread"+finalI+":"+threadLocalUserId.get());
                    System.out.println(String.format("Thread %d : %d",finalI,userId));
                }
            }).start();
        }*/


    }
    public static void testBlockQueue(){
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        new Thread(new Producer(queue)).start();
        new Thread(new Comsumer(queue),"comsumer1").start();
        new Thread(new Comsumer(queue),"comsumer2").start();
    }

    public static void testExecutor(){
       // ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i){
                    sleep(1000);
                    System.out.println(String.format("Thread 1:%d",i));
                }

            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i){
                    sleep(1000);
                    System.out.println(String.format("Thread 2:%d",i));
                }

            }
        });
        service.shutdown();
        while (!service.isTerminated()){
            sleep(1000);
            System.out.println("wait for termination.");
        }
    }
    public static void testFuture(){
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                for (int i = 0; i < 5; ++i){
                    sleep(1000);
                }
                return 10;
            }
        });
        service.shutdown();
        while (!future.isDone()){
            sleep(1000);
            System.out.println("wait for done");
        }
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //testThread();
        //testSynchronized();
        testBlockQueue();
        //testThreadLocal();
        //testExecutor();
        //testFuture();

    }
}
