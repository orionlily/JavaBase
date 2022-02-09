package com.orion.pool;

import com.orion.SmallTool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池，模拟线程不安全例子
 *
 * @author Administrator
 */
public class PoolUnSafeSimulation {

    private static ThreadPoolExecutor the = new ThreadPoolExecutor(3, 5, 5
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(16), SmallTool.getCustomThreadFactory());

    //static AtomicInteger sum = new AtomicInteger(0);
    static int sum = 0;

    public static void main(String[] args) {
        CountDownLatch mainLatch = new CountDownLatch(10);
        Runnable r = () -> {
            //synchronized (CountdownLatchTs4444.class) {
                for (int i = 0; i < 1_000_000; i++) {
                    //sum.incrementAndGet();
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
/**
 * 运行结果：（使用原子类或synchronize的话结果就正常）
 *
 1644389250574	|	11	|	pool-llc-thread-1	|	jisuan
 1644389250574	|	12	|	pool-llc-thread-2	|	jisuan
 1644389250577	|	12	|	pool-llc-thread-2	|	jisuan
 1644389250577	|	13	|	pool-llc-thread-3	|	jisuan
 1644389250577	|	11	|	pool-llc-thread-1	|	jisuan
 1644389250577	|	12	|	pool-llc-thread-2	|	jisuan
 1644389250577	|	13	|	pool-llc-thread-3	|	jisuan
 1644389250577	|	11	|	pool-llc-thread-1	|	jisuan
 1644389250577	|	12	|	pool-llc-thread-2	|	jisuan
 1644389250577	|	13	|	pool-llc-thread-3	|	jisuan
 1644389250578	|	1	|	main	|	结果为 : 5801184
 */
