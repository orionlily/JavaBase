package com.orion.pool;

import com.orion.SmallTool;

import java.util.concurrent.*;

/**
 * 测试线程池，模拟线程不安全例子
 *
 * @author Administrator
 */
public class PoolUnSafeSimulation {

    private static ThreadPoolExecutor the = new ThreadPoolExecutor(3, 5, 5
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(16), SmallTool.getCustomThreadFactory());

    static int sum = 0;

    public static void main(String[] args) {
        CountDownLatch mainLatch = new CountDownLatch(10);
        Runnable r = () -> {
            //synchronized (CountdownLatchTs4444.class) {
                for (int i = 0; i < 1_000_000; i++) {
                    sum++;
                }
                mainLatch.countDown();
                SmallTool.printTimeAndThread("jisuan");
            //}
        };


        for (int i = 0; i < 10; i++) {
            the.execute(r);
        }
        try {
            mainLatch.await();
            SmallTool.printTimeAndThread("结果为 : " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            the.shutdown();
        }

    }
}
