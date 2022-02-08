package com.orion.pool.exception;

import com.orion.SmallTool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
public class CustomThreadGroupHandleException {

    static class CustomThreadGroup extends ThreadGroup {
        public CustomThreadGroup(String name) {
            super(name);
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            SmallTool.printTimeAndThread(
                    "继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常："
                            + (e != null ? e.getLocalizedMessage() : ""));
        }
    }

    static class CustomThreadFactory implements ThreadFactory {
        private final ThreadGroup group = new CustomThreadGroup("llc-group");
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            namePrefix = "pool-llc-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }


    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5
                , 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), new CustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread(" 正在放弃任务：" + r + " , 所属executor : " + executor));

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
 1644318551163	|	1	|	main	|	 正在放弃任务：com.orion.pool.exception.CustomThreadGroupHandleException$$Lambda$2/1023892928@7699a589 , 所属executor : java.util.concurrent.ThreadPoolExecutor@58372a00[Running, pool size = 5, active threads = 5, queued tasks = 4, completed tasks = 0]
 1644318551165	|	11	|	pool-llc-thread-1	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551165	|	12	|	pool-llc-thread-2	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551166	|	13	|	pool-llc-thread-3	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551167	|	14	|	pool-llc-thread-4	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551167	|	15	|	pool-llc-thread-5	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551168	|	17	|	pool-llc-thread-6	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551168	|	18	|	pool-llc-thread-7	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551169	|	19	|	pool-llc-thread-8	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
 1644318551171	|	20	|	pool-llc-thread-9	|	继承ThreadGroup并重写其uncaughtException方法来设置异常处理，处理其中线程抛出的异常：/ by zero
*/
