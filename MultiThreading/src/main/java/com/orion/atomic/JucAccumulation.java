package com.orion.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用juc的原子类来进行累加，juc基本是cas乐观锁，自旋锁来保证线程安全问题
 *
 * @author Administrator
 */
public class JucAccumulation {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                count.incrementAndGet();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread tempThread = new Thread(runnable, "t" + i);
            tempThread.start();
            try {
                tempThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("count result :" + count.get());
    }

}
