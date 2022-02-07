package com.orion.base;

/**
 * 线程的异常处理
 *
 * 可以单独为某个线程设置异常处理，也可以直接调用Thread的静态方法设置全局的线程异常处理，如果它们都存在，则线程独有的异常处理优先。
 *
 * @author Administrator
 */
public class ThreadExceptionHandle {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("test thread Exception");
            throw new RuntimeException("custom thread runtime exception");
        });

        //设置单个线程的自定义异常处理
        thread.setUncaughtExceptionHandler((t, e) ->
                System.out.println("single thread ex handler : catch exception from thread : " + t.getName() + " , msg : " + e.getLocalizedMessage()));

        //设置全局的线程自定义异常处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                System.out.println("global thread ex handler : catch exception from thread : " + t.getName() + " , msg : " + e.getLocalizedMessage()));
        thread.start();
    }
}
/**
 * 运行结果：
 * <p>
 * 不设置自定义的异常处理：
 * test thread Exception
 * Exception in thread "Thread-0" java.lang.RuntimeException: custom thread runtime exception
 * at com.orion.base.ThreadExceptionHandle.lambda$main$0(ThreadExceptionHandle.java:10)
 * at java.lang.Thread.run(Thread.java:748)
 *
 * 设置单个线程的自定义异常处理:
 * test thread Exception
 * single thread ex handler : catch exception from thread : Thread-0 , msg : custom thread runtime exception
 *
 *设置全局的线程自定义异常处理:
 * test thread Exception
 * global thread ex handler : catch exception from thread : Thread-0 , msg : custom thread runtime exception
 */
