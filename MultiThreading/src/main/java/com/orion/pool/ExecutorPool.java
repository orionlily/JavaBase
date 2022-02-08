package com.orion.pool;

import com.orion.SmallTool;

import java.util.concurrent.*;

/**
 * 简单实用线程池，不建议直接使用Executors的，建议使用自行 new ThreadPoolExecutor
 *
 * @author Administrator
 */
public class ExecutorPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(2);
            return "call pool";
        };

        Future<String> future = executorService.submit(callable);
        try {
            //String s = future.get(3, TimeUnit.SECONDS); //超过睡眠时间，满足睡眠时长
            String s = future.get(1, TimeUnit.SECONDS); //少于睡眠时间，抛超时异常
            // s = future.get(); //一直阻塞等到结果出来
            SmallTool.printTimeAndThread("结果为" + s);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }


        //ExecutorService es = Executors.newCachedThreadPool(); //里面包含多个线程，几乎同时执行完下面2个任务
        ExecutorService es = Executors.newSingleThreadExecutor(); //只有一个线程，执行过程中间间隔睡眠时间
        Runnable runnable = () -> {
            SmallTool.sleepMillis(2000);
            SmallTool.printTimeAndThread("runnable pool");
        };
        try {
            es.execute(runnable);
            es.submit(runnable, Void.class);
        }finally {
            es.shutdown();
        }
    }
}
