package com.orion.pool.exception;

import com.orion.SmallTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池异常处理，默认是不处理的，直接往外抛
 * <p>
 * 处理方式：
 * 1、自行try catch 处理
 * 2、自定义线程池,主要是重写afterExecute(Runnable r, Throwable t)方法
 * 3、自定义ThreadFactory并且将自定义的Thread.setUncaughtExceptionHandler传给factory
 * 4、设置线程组传递给factory
 * 5、采用submit提交任务，从而返回future，而future可以获取的exception
 *
 * 线程池发生异常的时候，线程池的执行是不会被中断的，发生异常的线程会被删除掉，而线程池也会重新生成线程放回到池中。
 *
 * @author Administrator
 */
public class ThreadPoolTryCatchException {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService execute = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                SmallTool.getCustomThreadFactory());

        execute.execute(() -> SmallTool.printTimeAndThread("简单无异常的任务"));
        TimeUnit.SECONDS.sleep(5);
        execute.execute(new Run1());
    }


    private static class Run1 implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (true) {
                count++;
                SmallTool.printTimeAndThread("count=" + count);

                if (count == 10) {
                    try {
                        System.out.println(1 / 0);
                    } catch (Exception e) {
                        SmallTool.printTimeAndThread("Exception : " + e.getLocalizedMessage());
                    }
                }

                if (count == 20) {
                    SmallTool.printTimeAndThread("count=====" + count);
                    break;
                }
            }
            System.out.println("break");
        }
    }
}
/**
 * 运行结果：
 * 1644307851695	|	11	|	pool-1-thread-1	|	简单无异常的任务
 * Exception in thread "pool-1-thread-1" java.lang.ArithmeticException: / by zero
 * at com.orion.pool.exception.ThreadPoolTryCatchException$Run1.run(ThreadPoolTryCatchException.java:37)
 * at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=1
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=2
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=3
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=4
 * at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=5
 * at java.lang.Thread.run(Thread.java:748)
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=6
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=7
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=8
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=9
 * 1644307856701	|	11	|	pool-1-thread-1	|	count=10
 */