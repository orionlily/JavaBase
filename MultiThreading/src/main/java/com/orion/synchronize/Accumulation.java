package com.orion.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * 累加多线程问题,不加synchronize会出现结果不是1千万，因为count++不是原子操作，多个线程操作共享变量count，
 * 必然发生多线程安全问题，结果是少于1千万。
 *
 * @author Administrator
 */
public class Accumulation {
    private static int count = 0;
    private static Object lock = new Object();

    public static void main(String[] args) {
        Object o = new Object();
        Runnable r = () -> {
            //synchronized (Accumulation.class) {
            synchronized (lock) {
                for (int i = 0; i < 1000000; i++) {
                    count++;
                }
                System.out.println(Thread.currentThread() + "-- mmm");
            }
            System.out.println(Thread.currentThread() + "-- haha");
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(r, "t" + i);
            thread.start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result count : " + count);
    }
}
/**
 * 运行结果：mmm 和 haha 的位置没变，synchronize禁止重排序
 Thread[t0,5,main]-- mmm
 Thread[t0,5,main]-- haha
 Thread[t1,5,main]-- mmm
 Thread[t1,5,main]-- haha
 Thread[t2,5,main]-- mmm
 Thread[t2,5,main]-- haha
 Thread[t3,5,main]-- mmm
 Thread[t3,5,main]-- haha
 Thread[t4,5,main]-- mmm
 Thread[t4,5,main]-- haha
 Thread[t5,5,main]-- mmm
 Thread[t5,5,main]-- haha
 Thread[t6,5,main]-- mmm
 Thread[t6,5,main]-- haha
 Thread[t7,5,main]-- mmm
 Thread[t7,5,main]-- haha
 Thread[t8,5,main]-- mmm
 Thread[t8,5,main]-- haha
 Thread[t9,5,main]-- mmm
 Thread[t9,5,main]-- haha
 result count : 10000000
 *
 */
