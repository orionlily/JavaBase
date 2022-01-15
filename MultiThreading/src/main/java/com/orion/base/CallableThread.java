package com.orion.base;

import java.util.concurrent.*;

/**
 * 使用callable
 *
 * @author Administrator
 */
public class CallableThread {

    public static void main(String[] args) {
        Callable<String> callable = () -> {
            System.out.println(Thread.currentThread() + " in call and sleep 2s");
            TimeUnit.SECONDS.sleep(2);
            return Thread.currentThread() + " callable result";
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            String s = null;
           /* if (futureTask.isDone()) {
                s = futureTask.get();
                System.out.println(s);
            }*/
            s = futureTask.get(3, TimeUnit.SECONDS); //1 :timeout
            //s = futureTask.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            System.out.println("get result is InterruptedException" );
        } catch (ExecutionException e) {
            System.out.println("get result is ExecutionException");
        } catch (TimeoutException e) {
            System.out.println("get result is TimeoutException");
        }
    }
}
