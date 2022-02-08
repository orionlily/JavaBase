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
 * <p>
 * 线程池发生异常的时候，线程池的执行是不会被中断的，发生异常的线程会被删除掉，而线程池也会重新生成线程放回到池中。
 *
 * @author Administrator
 */
public class FutureHandleException {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5
                , 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), SmallTool.getCustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread(" 正在放弃任务：" + r + " , 所属executor : " + executor));

        Runnable r = () -> {
            int i = 1 / 0;
            //SmallTool.printTimeAndThread("jjaja");
        };

        for (int i = 0; i < 10; i++) {
            Future future = pool.submit(r);
            try {
                future.get();
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("中断异常：" + e.getMessage());
            } catch (ExecutionException e) {
                SmallTool.printTimeAndThread("线程池执行异常：" + e.getMessage());
            }
        }
    }
}
/**
 * 运行结果：
 * <p>
 * 1644319376454	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376456	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376456	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376457	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376457	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376457	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376457	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376457	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376458	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 * 1644319376458	|	1	|	main	|	线程池执行异常：java.lang.ArithmeticException: / by zero
 */
