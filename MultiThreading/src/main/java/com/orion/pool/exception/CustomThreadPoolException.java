package com.orion.pool.exception;

import com.orion.SmallTool;

import java.util.concurrent.*;

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
public class CustomThreadPoolException {

    public static void main(String[] args) {
        CustomThreadPoolWithHandleEx pool = new CustomThreadPoolWithHandleEx(3, 5
                , 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), Executors.defaultThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread(" 正在放弃任务：" + r + " , 所属executor : " + executor));

        Runnable r = () -> {
            int i = 1 / 0;
            //SmallTool.printTimeAndThread("jjaja");
        };

        for (int i = 0; i < 10; i++) {
            pool.execute(r);
        }
    }


    static class CustomThreadPoolWithHandleEx extends ThreadPoolExecutor {

        public CustomThreadPoolWithHandleEx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit
                , BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            SmallTool.printTimeAndThread("自定义线程池来处理其中线程抛出的异常：" + (t != null ? t.getLocalizedMessage() : ""));
        }
    }
}
/**
 * 运行结果：
 * 可见线程标号已经超过了maxPoolSize，可以知道是重新生成了新的线程了，processWorkerExit方法里面先remove再addWorker
 *
 *
 * Exception in thread "pool-1-thread-3" Exception in thread "pool-1-thread-1" Exception in thread "pool-1-thread-2" Exception in thread "pool-1-thread-4" Exception in thread "pool-1-thread-5" 1644316999262	|	15	|	pool-1-thread-3	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 1644316999262	|	1	|	main	|	 正在放弃任务：com.orion.pool.exception.CustomThreadPoolException$$Lambda$2/1929600551@25618e91 , 所属executor : com.orion.pool.exception.CustomThreadPoolException$CustomThreadPoolWithHandleEx@2cfb4a64[Running, pool size = 5, active threads = 5, queued tasks = 4, completed tasks = 0]
 1644316999262	|	13	|	pool-1-thread-1	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 1644316999262	|	14	|	pool-1-thread-2	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 1644316999262	|	16	|	pool-1-thread-4	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 1644316999262	|	17	|	pool-1-thread-5	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 1644316999266	|	18	|	pool-1-thread-6	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 java.lang.ArithmeticException: / by zero
 1644316999268	|	19	|	pool-1-thread-7	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 1644316999268	|	21	|	pool-1-thread-8	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 1644316999269	|	22	|	pool-1-thread-9	|	自定义线程池来处理其中线程抛出的异常：/ by zero
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 Exception in thread "pool-1-thread-6" java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 Exception in thread "pool-1-thread-7" java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 Exception in thread "pool-1-thread-8" java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 Exception in thread "pool-1-thread-9" java.lang.ArithmeticException: / by zero
 at com.orion.pool.exception.CustomThreadPoolException.lambda$main$1(CustomThreadPoolException.java:27)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 at java.lang.Thread.run(Thread.java:748)
 *
 */
