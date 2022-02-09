package com.orion.jucutil;

import com.orion.SmallTool;

import java.util.concurrent.*;

/**
 * CountDownLatch就像是个计数器，一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行
 *
 * @author Administrator
 */
public class CountdownLatchTs {

    private static ThreadPoolExecutor the = new ThreadPoolExecutor(3, 5, 5
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), SmallTool.getCustomThreadFactory());

    public static void main(String[] args) {
        CountDownLatch mainLatch = new CountDownLatch(10);

        Callable<Integer> r = () -> {
            int cnt = 0;
            for (int i = 0; i < 100_000_000L; i++) {
                cnt++;
            }
            SmallTool.printTimeAndThread("正在计算...cnt = " + cnt);
            return cnt;
        };

        int sum = 0;

        for (int i = 0; i < 10; i++) {
             Future<Integer> future = the.submit(r);
            try {
                sum += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                mainLatch.countDown();
            }
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
 * 运行结果：
 *
 1644395249457	|	11	|	pool-llc-thread-1	|	正在计算...cnt = 100000000
 1644395249533	|	12	|	pool-llc-thread-2	|	正在计算...cnt = 100000000
 1644395249683	|	13	|	pool-llc-thread-3	|	正在计算...cnt = 100000000
 1644395249795	|	11	|	pool-llc-thread-1	|	正在计算...cnt = 100000000
 1644395249911	|	12	|	pool-llc-thread-2	|	正在计算...cnt = 100000000
 1644395250017	|	13	|	pool-llc-thread-3	|	正在计算...cnt = 100000000
 1644395250128	|	11	|	pool-llc-thread-1	|	正在计算...cnt = 100000000
 1644395250239	|	12	|	pool-llc-thread-2	|	正在计算...cnt = 100000000
 1644395250359	|	13	|	pool-llc-thread-3	|	正在计算...cnt = 100000000
 1644395250458	|	11	|	pool-llc-thread-1	|	正在计算...cnt = 100000000
 1644395250458	|	1	|	main	|	结果为 : 1000000000
 *
 */