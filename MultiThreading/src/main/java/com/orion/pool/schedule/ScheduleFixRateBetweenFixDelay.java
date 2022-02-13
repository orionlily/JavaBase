package com.orion.pool.schedule;

import com.orion.SmallTool;

import java.util.concurrent.*;

/**
 * ScheduledExecutorService中fixRate与fixDelay的区别
 *
 * @author Administrator
 */
public class ScheduleFixRateBetweenFixDelay {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3
                , SmallTool.getCustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread("放弃任务：" + r + "，所属线程池：" + executor));

        // fixRate,如果任务所需时间超过设置时间，会等待任务完成后(间隔3s)才会进行下一次的任务，否则还是用设置的时间2s
        // fixRateExecManyTimes(executorService);

        // FixedDelay,如果任务所需时间超过设置时间，会等待任务完成后(用时2s)再delay(1s)才会进行下一次的任务,
        // 总用时就是taskTime + delayTime，否则就是delayTime
        scheduleWithFixedDelayExecManyTimes(executorService);

    }

    private static void fixRateExecManyTimes(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行fix rate scheduled task");
        Runnable runnable = () ->{
            SmallTool.printTimeAndThread("fix rate runnable task");
            //超过period
            SmallTool.sleepMillis(3000);
        };
        executorService.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
        /**
         * 运行结果： 可见延迟0秒执行，之后每隔3秒（睡眠时间）执行一次,比设置的2秒长，证明会等待完成才会进行下一次任务
         *
         1644424321662	|	1	|	main	|	马上进行fix rate scheduled task
         1644424321669	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644424324680	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644424327693	|	13	|	pool-llc-thread-2	|	fix rate runnable task
         1644424330708	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644424333722	|	14	|	pool-llc-thread-3	|	fix rate runnable task
         1644424336727	|	14	|	pool-llc-thread-3	|	fix rate runnable task
         1644424339728	|	14	|	pool-llc-thread-3	|	fix rate runnable task
         */
    }

    private static void scheduleWithFixedDelayExecManyTimes(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行scheduleWithFixedDelay的task");
        Runnable runnable = () -> {
            SmallTool.printTimeAndThread("schedule with fixed delay runnable task");
            //超过period
            SmallTool.sleepMillis(2000);
        };
        ScheduledFuture schedule = executorService.scheduleWithFixedDelay(runnable, 0,1, TimeUnit.SECONDS);
        try {
            SmallTool.printTimeAndThread("callable schedule result : " + schedule.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /**
         * 运行结果： 可见延迟0秒执行,之后每次任务会等待任务完成后(用时2s)再delay(1s)才会进行下一次的任务
         *
         1644730884860	|	1	|	main	|	马上进行scheduleWithFixedDelay的task
         1644730884860	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644730887864	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644730890869	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644730893903	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644730896908	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         1644730899909	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         */
    }
}
