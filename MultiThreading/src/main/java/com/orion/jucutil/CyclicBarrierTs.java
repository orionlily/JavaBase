package com.orion.jucutil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 循环栏栅
 *
 * 所有线程都必须全部就位才启动，调用await()方法使得线程阻塞，如果是最后一个线程(CyclicBarrier的parties最大值)准备好，就会执行
 * CyclicBarrier中的runnableCommand，并且signalAll所有线程，并一起往下执行
 *
 */
public class CyclicBarrierTs
{
    //指定必须有6个运动员到达才行
    private static CyclicBarrier barrier = new CyclicBarrier(6, () -> {
        System.out.println("所有运动员入场，裁判员一声令下！！！！！");
    });

    public static void main(String[] args) {
        System.out.println("运动员准备进场，全场欢呼............");
        ExecutorService service = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员，进场");
                            barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 运动员出发");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

/**
 * 运行结果：

 运动员准备进场，全场欢呼............
 pool-1-thread-1 运动员，进场
 pool-1-thread-2 运动员，进场
 pool-1-thread-3 运动员，进场
 pool-1-thread-4 运动员，进场
 pool-1-thread-5 运动员，进场
 pool-1-thread-6 运动员，进场
 所有运动员入场，裁判员一声令下！！！！！
 pool-1-thread-6 运动员出发
 pool-1-thread-1 运动员出发
 pool-1-thread-2 运动员出发
 pool-1-thread-3 运动员出发
 pool-1-thread-5 运动员出发
 pool-1-thread-4 运动员出发
 *
 *
 */