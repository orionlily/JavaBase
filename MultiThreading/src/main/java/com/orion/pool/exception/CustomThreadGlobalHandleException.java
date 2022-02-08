package com.orion.pool.exception;

import com.orion.SmallTool;

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
 * <p>
 * 线程池发生异常的时候，线程池的执行是不会被中断的，发生异常的线程会被删除掉，而线程池也会重新生成线程放回到池中。
 *
 * @author Administrator
 */
public class CustomThreadGlobalHandleException {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5
                , 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), SmallTool.getCustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread(" 正在放弃任务：" + r + " , 所属executor : " + executor));

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            SmallTool.printTimeAndThread(
                    "使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常："
                            + (e != null ? e.getLocalizedMessage() : ""));
        });

        Runnable r = () -> {
            int i = 1 / 0;
            //SmallTool.printTimeAndThread("jjaja");
        };

        for (int i = 0; i < 10; i++) {
            pool.execute(r);
        }
    }
}
/**
 * 运行结果：
 * 可见线程标号已经超过了maxPoolSize，可以知道是重新生成了新的线程了，processWorkerExit方法里面先remove再addWorker
 *
 1644317663405	|	13	|	pool-llc-thread-1	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663406	|	1	|	main	|	 正在放弃任务：com.orion.pool.exception.CustomThreadGlobalHandleException$$Lambda$3/1929600551@25618e91 , 所属executor : java.util.concurrent.ThreadPoolExecutor@2cfb4a64[Running, pool size = 5, active threads = 5, queued tasks = 4, completed tasks = 1]
 1644317663408	|	15	|	pool-llc-thread-3	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663408	|	14	|	pool-llc-thread-2	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663408	|	16	|	pool-llc-thread-4	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663410	|	20	|	pool-llc-thread-8	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663410	|	17	|	pool-llc-thread-5	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663410	|	18	|	pool-llc-thread-6	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663411	|	21	|	pool-llc-thread-7	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
 1644317663412	|	22	|	pool-llc-thread-9	|	使用Thread的静态方法setDefaultUncaughtExceptionHandler来设置全局异常处理，处理其中线程抛出的异常：/ by zero
*/
